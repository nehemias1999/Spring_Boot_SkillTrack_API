package com.nsalazar.skill_track.instructor.application.port.in;

import com.nsalazar.skill_track.instructor.domain.Address;
import com.nsalazar.skill_track.instructor.domain.Instructor;

import java.util.UUID;

/**
 * Inbound port for partially updating an instructor.
 */
public interface UpdateInstructorUseCase {

    /**
     * Command for updating an instructor. All fields are optional (null = keep existing value).
     * Providing an {@link Address} with all-null fields is treated as "no address".
     */
    record UpdateInstructorCommand(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String bio,
            Address address
    ) {}

    /**
     * Updates the instructor with the provided non-null fields.
     *
     * @param command the update command
     * @return the updated instructor
     */
    Instructor updateInstructor(UpdateInstructorCommand command);
}
