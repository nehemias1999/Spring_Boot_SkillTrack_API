package com.nsalazar.skill_track.student.application.port.in;

import com.nsalazar.skill_track.student.domain.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Input port for the create-student use case.
 * Defines the command record and the single method that drives student creation.
 */
public interface CreateStudentUseCase {

    /**
     * Immutable command object carrying the data required to create a new student.
     *
     * @param firstName the student's first name (must not be blank)
     * @param lastName  the student's last name (must not be blank)
     * @param email     the student's email address (must be a valid email and not blank)
     */
    record CreateStudentCommand(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @Email @NotBlank String email
    ) {}

    /**
     * Creates a new student from the supplied command.
     *
     * @param command the validated command containing student details
     * @return the newly created {@link Student} with its assigned id
     */
    Student createStudent(CreateStudentCommand command);
}
