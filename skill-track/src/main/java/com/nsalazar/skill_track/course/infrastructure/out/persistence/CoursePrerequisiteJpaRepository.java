package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link CoursePrerequisiteJpaEntity} join-table entity.
 * Uses the composite {@link CoursePrerequisiteId} as the primary key type.
 *
 * <p>Demonstrates {@code @EmbeddedId} composite primary key usage with Spring Data JPA —
 * the repository transparently handles composite key lookups and deletes.
 */
public interface CoursePrerequisiteJpaRepository extends JpaRepository<CoursePrerequisiteJpaEntity, CoursePrerequisiteId> {
}
