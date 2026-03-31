package com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for updating enrollment progress.
 *
 * @param progressPercentage the new progress value (0–100)
 */
public record UpdateProgressRequest(
        @NotNull(message = "Progress percentage is required")
        @Min(value = 0, message = "Progress must be at least 0")
        @Max(value = 100, message = "Progress must be at most 100")
        Integer progressPercentage
) {}
