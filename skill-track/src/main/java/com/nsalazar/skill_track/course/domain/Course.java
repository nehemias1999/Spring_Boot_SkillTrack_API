package com.nsalazar.skill_track.course.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Domain record representing a course offered on the platform.
 *
 * @param id              unique identifier; {@code null} when not yet persisted
 * @param title           the course title
 * @param description     optional description of the course content
 * @param price           the enrolment price (must be positive)
 * @param instructorId    the id of the instructor who owns this course
 * @param category        the subject area of the course
 * @param difficulty      the difficulty level of the course
 * @param durationHours   estimated total duration in hours
 * @param status          the publication status of the course
 * @param version         optimistic-locking token managed by JPA; {@code null} for new entities
 * @param keywords        free-form keywords stored in a separate collection table via {@code @ElementCollection}
 * @param prerequisiteIds ids of courses that must be completed before enrolling; managed via a self-referential
 *                        many-to-many join entity ({@code CoursePrerequisiteJpaEntity}) with a composite PK
 * @param tagNames        names of taxonomy tags attached to this course via a proper many-to-many join table
 *                        ({@code course_tags}) linking to a shared {@code TagJpaEntity}
 */
public record Course(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        UUID instructorId,
        CourseCategory category,
        CourseDifficulty difficulty,
        Integer durationHours,
        CourseStatus status,
        Long version,
        Set<String> keywords,
        Set<UUID> prerequisiteIds,
        Set<String> tagNames
) {}
