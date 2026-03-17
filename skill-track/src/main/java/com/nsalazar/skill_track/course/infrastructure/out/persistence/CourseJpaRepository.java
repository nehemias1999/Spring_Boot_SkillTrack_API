package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link CourseJpaEntity}.
 * Provides standard CRUD operations inherited from {@link JpaRepository}.
 */
interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long> {}
