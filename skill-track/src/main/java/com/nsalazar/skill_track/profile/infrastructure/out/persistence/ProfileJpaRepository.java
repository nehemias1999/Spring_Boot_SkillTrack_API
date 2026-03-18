package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link ProfileJpaEntity}.
 * Provides standard CRUD operations and derived queries for student-based lookups.
 */
interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, UUID> {

    /**
     * Finds the profile associated with the given student id.
     *
     * @param studentId the id of the owning student
     * @return an {@link Optional} containing the profile entity if found, or empty otherwise
     */
    Optional<ProfileJpaEntity> findByStudentId(UUID studentId);

    /**
     * Checks whether a profile record exists for the given student id.
     *
     * @param studentId the id of the student to check
     * @return {@code true} if a profile exists, {@code false} otherwise
     */
    boolean existsByStudentId(UUID studentId);
}
