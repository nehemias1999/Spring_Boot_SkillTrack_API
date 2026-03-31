package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Request DTO for creating a course.
 */
public record CreateCourseRequest(
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Description is required") String description,
        @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigDecimal price,
        CourseCategory category,
        CourseDifficulty difficulty,
        @PositiveOrZero(message = "Duration must be zero or positive") Integer durationHours
) {}
