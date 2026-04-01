package com.nsalazar.skill_track.lesson.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link LessonJpaEntity} and its subclasses.
 * Spring Data automatically resolves polymorphic queries: a call to {@code findAll()}
 * returns a mix of {@link VideoLessonJpaEntity} and {@link TextLessonJpaEntity} instances,
 * each with their subclass table joined in.
 */
interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, UUID> {

    /**
     * Returns all lessons for a given course ordered by their position.
     * Hibernate generates a JOIN between the {@code lessons} base table and the
     * appropriate subclass table ({@code video_lessons} or {@code text_lessons})
     * based on the discriminator value.
     *
     * @param courseId the owning course id
     * @return lessons ordered by position ascending
     */
    List<LessonJpaEntity> findByCourseIdOrderByPositionAsc(UUID courseId);
}
