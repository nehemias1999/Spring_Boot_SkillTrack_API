package com.nsalazar.skill_track.course.domain;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain record representing a course offered on the platform.
 *
 * @param id            unique identifier; {@code null} when not yet persisted
 * @param title         the course title
 * @param description   optional description of the course content
 * @param price         the enrolment price (must be positive)
 * @param instructorId  the id of the instructor who owns this course
 * @param category      the subject area of the course
 * @param difficulty    the difficulty level of the course
 * @param durationHours estimated total duration in hours
 * @param status        the publication status of the course
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
        CourseStatus status
) {}
