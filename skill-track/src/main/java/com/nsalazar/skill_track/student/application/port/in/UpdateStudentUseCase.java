package com.nsalazar.skill_track.student.application.port.in;

import com.nsalazar.skill_track.student.domain.Student;
import java.util.UUID;

/**
 * Inbound port for partially updating a student.
 */
public interface UpdateStudentUseCase {
    /**
     * Command for updating a student. All fields are optional (null = keep existing value).
     */
    record UpdateStudentCommand(UUID id, String firstName, String lastName, String email) {}

    /**
     * Updates a student with the provided non-null fields.
     *
     * @param command the update command with at least one non-null field
     * @return the updated student
     */
    Student updateStudent(UpdateStudentCommand command);
}
