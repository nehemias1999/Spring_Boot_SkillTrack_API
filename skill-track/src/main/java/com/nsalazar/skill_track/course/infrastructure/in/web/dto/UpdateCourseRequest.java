package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.domain.CourseStatus;

import java.math.BigDecimal;

/**
 * Request DTO for partially updating a course. All fields are optional — null means keep existing value.
 */
public record UpdateCourseRequest(
        String title,
        String description,
        BigDecimal price,
        CourseCategory category,
        CourseDifficulty difficulty,
        Integer durationHours,
        CourseStatus status
) {}
