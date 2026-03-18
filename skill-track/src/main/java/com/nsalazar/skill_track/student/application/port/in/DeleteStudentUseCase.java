package com.nsalazar.skill_track.student.application.port.in;

import java.util.UUID;

/**
 * Inbound port for deleting a student by id.
 */
public interface DeleteStudentUseCase {
    /**
     * Deletes the student with the given id.
     *
     * @param id the student id
     */
    void deleteStudent(UUID id);
}
