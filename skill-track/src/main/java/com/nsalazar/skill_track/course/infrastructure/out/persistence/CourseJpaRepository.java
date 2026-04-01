package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link CourseJpaEntity}.
 * Provides standard CRUD operations inherited from {@link JpaRepository}.
 */
public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, UUID> {

    /**
     * Loads a course by id with its instructor eagerly fetched via JOIN FETCH,
     * preventing the N+1 select problem when the instructor is accessed.
     *
     * @param id the course id
     * @return an {@link Optional} containing the fully initialised course entity if found
     */
    @Query("SELECT c FROM CourseJpaEntity c JOIN FETCH c.instructor WHERE c.id = :id")
    Optional<CourseJpaEntity> findByIdWithInstructor(@Param("id") UUID id);
}
