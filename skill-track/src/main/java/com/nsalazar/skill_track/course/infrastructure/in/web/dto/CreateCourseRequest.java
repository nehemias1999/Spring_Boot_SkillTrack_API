package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Request DTO for the create-course endpoint.
 * The instructor id is supplied via the URL path variable, not included here.
 *
 * @param title       the course title (required)
 * @param description optional description of the course content
 * @param price       the enrolment price (required, must be positive)
 */
public record CreateCourseRequest(
        @NotBlank(message = "Title is required")
        String title,
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price
) {}
