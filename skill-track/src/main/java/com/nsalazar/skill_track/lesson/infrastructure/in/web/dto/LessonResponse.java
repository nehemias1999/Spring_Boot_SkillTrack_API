package com.nsalazar.skill_track.lesson.infrastructure.in.web.dto;

import com.nsalazar.skill_track.lesson.domain.LessonType;

import java.util.UUID;

/**
 * Response DTO for a course lesson.
 * Null type-specific fields (e.g. {@code videoUrl} for TEXT lessons) are omitted by Jackson.
 */
public record LessonResponse(
        UUID id,
        UUID courseId,
        String title,
        Integer position,
        LessonType type,
        String videoUrl,
        Integer durationSeconds,
        String content
) {}
