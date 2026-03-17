package com.nsalazar.skill_track.instructor.application.port.in;

import com.nsalazar.skill_track.instructor.domain.Instructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Input port for the create-instructor use case.
 * Defines the command record and the single method that drives instructor creation.
 */
public interface CreateInstructorUseCase {

    /**
     * Immutable command object carrying the data required to create a new instructor.
     *
     * @param firstName the instructor's first name (must not be blank)
     * @param lastName  the instructor's last name (must not be blank)
     * @param email     the instructor's email address (must be a valid email and not blank)
     * @param bio       optional free-text biography
     */
    record CreateInstructorCommand(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @Email @NotBlank String email,
            String bio
    ) {}

    /**
     * Creates a new instructor from the supplied command.
     *
     * @param command the validated command containing instructor details
     * @return the newly created {@link Instructor} with its assigned id
     */
    Instructor createInstructor(CreateInstructorCommand command);
}
