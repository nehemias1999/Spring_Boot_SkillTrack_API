package com.nsalazar.skill_track.instructor.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link InstructorJpaEntity}.
 * Provides standard CRUD operations and a derived email-existence query.
 */
interface InstructorJpaRepository extends JpaRepository<InstructorJpaEntity, Long> {

    /**
     * Checks whether an instructor with the given email address exists in the database.
     *
     * @param email the email address to check
     * @return {@code true} if a matching instructor record exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
