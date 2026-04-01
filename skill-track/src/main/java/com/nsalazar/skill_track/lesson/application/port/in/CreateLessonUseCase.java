package com.nsalazar.skill_track.lesson.application.port.in;

import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.domain.LessonType;

import java.util.UUID;

/**
 * Inbound port for creating a lesson within a course.
 * The command carries all variant fields; fields not applicable to the lesson type
 * should be left {@code null}.
 */
public interface CreateLessonUseCase {

    /**
     * Creates a new lesson for the specified course.
     *
     * @param command the validated creation command
     * @return the persisted {@link Lesson} with its generated id
     */
    Lesson createLesson(CreateLessonCommand command);

    /**
     * Command record for creating a lesson.
     *
     * @param courseId        the course this lesson belongs to
     * @param title           display title of the lesson
     * @param position        ordering position within the course (1-based)
     * @param type            {@link LessonType#VIDEO} or {@link LessonType#TEXT}
     * @param videoUrl        URL of the video (VIDEO lessons only)
     * @param durationSeconds duration in seconds (VIDEO lessons only)
     * @param content         markdown body (TEXT lessons only)
     */
    record CreateLessonCommand(
            UUID courseId,
            String title,
            Integer position,
            LessonType type,
            String videoUrl,
            Integer durationSeconds,
            String content
    ) {}
}
