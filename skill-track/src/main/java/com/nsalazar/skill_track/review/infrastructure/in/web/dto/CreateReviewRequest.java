package com.nsalazar.skill_track.review.infrastructure.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for submitting a course review.
 *
 * @param rating  star rating from 1 (worst) to 5 (best)
 * @param comment optional free-text comment
 */
public record CreateReviewRequest(
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        Integer rating,
        String comment
) {}
