package com.nsalazar.skill_track.instructor.domain.port.out;

import com.nsalazar.skill_track.instructor.domain.Instructor;

import java.util.Optional;

/**
 * Output port defining the persistence contract for {@link Instructor} entities.
 * Implementations are provided by the infrastructure layer.
 */
public interface InstructorRepositoryPort {

    /**
     * Persists the given instructor and returns the saved state (including any generated id).
     *
     * @param instructor the instructor to save
     * @return the persisted instructor with its assigned id
     */
    Instructor save(Instructor instructor);

    /**
     * Looks up an instructor by its unique identifier.
     *
     * @param id the instructor id to search for
     * @return an {@link Optional} containing the instructor if found, or empty otherwise
     */
    Optional<Instructor> findById(Long id);

    /**
     * Checks whether an instructor with the given email already exists.
     *
     * @param email the email address to check
     * @return {@code true} if an instructor with this email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
