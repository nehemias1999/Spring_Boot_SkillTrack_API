package com.nsalazar.skill_track.lesson.infrastructure.in.web.dto;

import com.nsalazar.skill_track.lesson.domain.LessonType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating a course lesson.
 * Type-specific fields ({@code videoUrl}/{@code durationSeconds} for VIDEO,
 * {@code content} for TEXT) are validated in the service layer after the type is known.
 */
public record CreateLessonRequest(
        @NotBlank(message = "Title is required") String title,
        @NotNull(message = "Position is required") @Min(value = 1, message = "Position must be at least 1") Integer position,
        @NotNull(message = "Type is required") LessonType type,
        String videoUrl,
        Integer durationSeconds,
        String content
) {}
