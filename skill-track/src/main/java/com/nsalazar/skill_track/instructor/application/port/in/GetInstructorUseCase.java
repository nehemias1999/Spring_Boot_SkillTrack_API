package com.nsalazar.skill_track.instructor.application.port.in;

import com.nsalazar.skill_track.instructor.domain.Instructor;

/**
 * Input port for the get-instructor use case.
 * Defines the contract for retrieving an instructor by identifier.
 */
public interface GetInstructorUseCase {

    /**
     * Retrieves an instructor by its unique identifier.
     *
     * @param id the id of the instructor to retrieve
     * @return the {@link Instructor} matching the given id
     */
    Instructor getInstructorById(Long id);
}
