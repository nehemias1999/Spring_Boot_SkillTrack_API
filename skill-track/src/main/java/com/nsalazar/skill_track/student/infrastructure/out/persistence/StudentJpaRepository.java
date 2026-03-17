package com.nsalazar.skill_track.student.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link StudentJpaEntity}.
 * Provides standard CRUD operations and a derived email-existence query.
 */
interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {

    /**
     * Checks whether a student with the given email address exists in the database.
     *
     * @param email the email address to check
     * @return {@code true} if a matching student record exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
