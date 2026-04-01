package com.nsalazar.skill_track.lesson.domain;

import java.util.UUID;

/**
 * Immutable domain record representing a course lesson.
 *
 * <p>A lesson belongs to exactly one course and has a {@link LessonType} that determines
 * which concrete subclass is stored in the database (JOINED inheritance strategy).
 *
 * <ul>
 *   <li>VIDEO lessons carry {@code videoUrl} and {@code durationSeconds}.</li>
 *   <li>TEXT lessons carry {@code content} (markdown body).</li>
 * </ul>
 *
 * Optional fields not applicable to a given type will be {@code null}.
 */
public record Lesson(
        UUID id,
        UUID courseId,
        String title,
        Integer position,
        LessonType type,

        // VIDEO-specific (null for TEXT lessons)
        String videoUrl,
        Integer durationSeconds,

        // TEXT-specific (null for VIDEO lessons)
        String content
) {}
