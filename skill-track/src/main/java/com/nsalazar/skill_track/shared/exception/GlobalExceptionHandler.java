package com.nsalazar.skill_track.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Centralized exception handler for all REST controllers.
 * Translates application exceptions into RFC 7807 {@link ProblemDetail} responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} and returns a 404 Not Found response.
     *
     * @param ex the exception describing the missing resource
     * @return a {@link ProblemDetail} with status 404 and the exception message as detail
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Resource Not Found");
        return problem;
    }

    /**
     * Handles {@link BusinessValidationException} and returns a 409 Conflict response.
     *
     * @param ex the exception describing the violated business rule
     * @return a {@link ProblemDetail} with status 409 and the exception message as detail
     */
    @ExceptionHandler(BusinessValidationException.class)
    public ProblemDetail handleBusinessValidation(BusinessValidationException ex) {
        log.warn("Business validation error: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Business Validation Error");
        return problem;
    }

    /**
     * Handles {@link DataIntegrityViolationException} (e.g. unique constraint violations at the DB level)
     * and returns a 409 Conflict response.
     *
     * @param ex the exception thrown by the persistence layer
     * @return a {@link ProblemDetail} with status 409
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "A record with the same unique identifier already exists.");
        problem.setTitle("Data Integrity Violation");
        return problem;
    }

    /**
     * Handles {@link ObjectOptimisticLockingFailureException} thrown when two concurrent transactions
     * attempt to update the same entity version, and returns a 409 Conflict response.
     *
     * @param ex the optimistic locking exception
     * @return a {@link ProblemDetail} with status 409 asking the client to retry
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLocking(ObjectOptimisticLockingFailureException ex) {
        log.warn("Optimistic locking failure for entity {}: {}", ex.getPersistentClass().getSimpleName(), ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "The resource was modified by another request. Please fetch the latest version and retry.");
        problem.setTitle("Optimistic Locking Failure");
        return problem;
    }

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a 422 Unprocessable Entity response
     * containing a map of field-level validation errors.
     *
     * @param ex the exception containing binding result with field errors
     * @return a {@link ProblemDetail} with status 422 and an {@code errors} property listing each field failure
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() == null ? "Invalid value" : fe.getDefaultMessage(),
                        (a, b) -> a
                ));
        log.warn("Validation failed with {} field error(s): {}", errors.size(), errors);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed");
        problem.setTitle("Validation Error");
        problem.setProperty("errors", errors);
        return problem;
    }
}
