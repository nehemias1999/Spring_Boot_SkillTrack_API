package com.nsalazar.skill_track.student.domain.port.out;

import com.nsalazar.skill_track.student.domain.Student;

import java.util.Optional;
import java.util.UUID;

/**
 * Output port defining the persistence contract for {@link Student} entities.
 * Implementations are provided by the infrastructure layer.
 */
public interface StudentRepositoryPort {

    /**
     * Persists the given student and returns the saved state (including any generated id).
     *
     * @param student the student to save
     * @return the persisted student with its assigned id
     */
    Student save(Student student);

    /**
     * Looks up a student by its unique identifier.
     *
     * @param id the student id to search for
     * @return an {@link Optional} containing the student if found, or empty otherwise
     */
    Optional<Student> findById(UUID id);

    /**
     * Checks whether a student with the given email already exists.
     *
     * @param email the email address to check
     * @return {@code true} if a student with this email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
