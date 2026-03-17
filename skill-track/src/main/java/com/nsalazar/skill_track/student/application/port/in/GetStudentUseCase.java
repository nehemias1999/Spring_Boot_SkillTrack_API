package com.nsalazar.skill_track.student.application.port.in;

import com.nsalazar.skill_track.student.domain.Student;

/**
 * Input port for the get-student use case.
 * Defines the contract for retrieving a student by identifier.
 */
public interface GetStudentUseCase {

    /**
     * Retrieves a student by its unique identifier.
     *
     * @param id the id of the student to retrieve
     * @return the {@link Student} matching the given id
     */
    Student getStudentById(Long id);
}
