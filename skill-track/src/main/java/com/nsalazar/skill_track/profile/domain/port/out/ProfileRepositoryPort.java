package com.nsalazar.skill_track.profile.domain.port.out;

import com.nsalazar.skill_track.profile.domain.Profile;

import java.util.Optional;
import java.util.UUID;

/**
 * Output port defining the persistence contract for {@link Profile} entities.
 * Implementations are provided by the infrastructure layer.
 */
public interface ProfileRepositoryPort {

    /**
     * Persists the given profile and returns the saved state (including any generated id).
     *
     * @param profile the profile to save
     * @return the persisted profile with its assigned id
     */
    Profile save(Profile profile);

    /**
     * Looks up a profile by the owning student's id.
     *
     * @param studentId the id of the student whose profile is requested
     * @return an {@link Optional} containing the profile if found, or empty otherwise
     */
    Optional<Profile> findByStudentId(UUID studentId);

    /**
     * Checks whether a profile already exists for the given student.
     *
     * @param studentId the id of the student to check
     * @return {@code true} if a profile exists for this student, {@code false} otherwise
     */
    boolean existsByStudentId(UUID studentId);

    /**
     * Deletes the profile associated with the given student id.
     *
     * @param studentId the student id
     */
    void deleteByStudentId(UUID studentId);
}
