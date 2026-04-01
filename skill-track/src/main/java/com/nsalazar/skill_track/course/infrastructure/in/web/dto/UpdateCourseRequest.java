package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.domain.CourseStatus;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Request DTO for partially updating a course. All fields are optional — null means keep existing value.
 * Providing an empty {@code keywords} set explicitly clears all keywords.
 */
public record UpdateCourseRequest(
        String title,
        String description,
        BigDecimal price,
        CourseCategory category,
        CourseDifficulty difficulty,
        Integer durationHours,
        CourseStatus status,
        Set<String> keywords
) {}
