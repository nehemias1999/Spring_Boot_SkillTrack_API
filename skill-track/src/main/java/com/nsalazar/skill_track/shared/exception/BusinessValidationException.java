package com.nsalazar.skill_track.shared.exception;

/**
 * Exception thrown when a business rule or constraint is violated.
 * Maps to an HTTP 409 Conflict response via {@link GlobalExceptionHandler}.
 */
public class BusinessValidationException extends RuntimeException {

    /**
     * Constructs a new {@code BusinessValidationException} with the given detail message.
     *
     * @param message a description of the violated business rule
     */
    public BusinessValidationException(String message) {
        super(message);
    }
}
