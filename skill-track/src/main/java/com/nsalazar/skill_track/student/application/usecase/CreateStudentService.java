package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Application service that handles the create-student use case.
 * Enforces the uniqueness-by-email business rule before persisting a new student.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class CreateStudentService implements CreateStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    /**
     * Creates a new student after verifying that no other student shares the same email.
     *
     * @param command the validated command containing the student's details
     * @return the persisted {@link Student} with its assigned id
     * @throws BusinessValidationException if a student with the given email already exists
     */
    @Override
    public Student createStudent(CreateStudentCommand command) {
        log.info("Creating student with email '{}'", command.email());
        if (studentRepositoryPort.existsByEmail(command.email())) {
            log.warn("Duplicate email detected: '{}'", command.email());
            throw new BusinessValidationException(
                    "A student with email '" + command.email() + "' already exists.");
        }
        Student student = new Student(null, command.firstName(), command.lastName(), command.email(), null);
        Student saved = studentRepositoryPort.save(student);
        log.info("Student created successfully with id {}", saved.id());
        return saved;
    }
}
