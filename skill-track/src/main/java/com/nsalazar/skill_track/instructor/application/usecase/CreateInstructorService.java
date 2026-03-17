package com.nsalazar.skill_track.instructor.application.usecase;

import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service that handles the create-instructor use case.
 * Enforces the uniqueness-by-email business rule before persisting a new instructor.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateInstructorService implements CreateInstructorUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;

    /**
     * Creates a new instructor after verifying that no other instructor shares the same email.
     *
     * @param command the validated command containing the instructor's details
     * @return the persisted {@link Instructor} with its assigned id
     * @throws BusinessValidationException if an instructor with the given email already exists
     */
    @Override
    public Instructor createInstructor(CreateInstructorCommand command) {
        log.info("Creating instructor with email '{}'", command.email());
        if (instructorRepositoryPort.existsByEmail(command.email())) {
            log.warn("Duplicate email detected: '{}'", command.email());
            throw new BusinessValidationException(
                    "An instructor with email '" + command.email() + "' already exists.");
        }
        Instructor instructor = new Instructor(null, command.firstName(), command.lastName(),
                command.email(), command.bio());
        Instructor saved = instructorRepositoryPort.save(instructor);
        log.info("Instructor created successfully with id {}", saved.id());
        return saved;
    }
}
