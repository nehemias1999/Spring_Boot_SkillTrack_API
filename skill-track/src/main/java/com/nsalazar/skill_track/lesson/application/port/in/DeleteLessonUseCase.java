package com.nsalazar.skill_track.lesson.application.port.in;

import java.util.UUID;

/**
 * Inbound port for deleting a lesson.
 */
public interface DeleteLessonUseCase {

    /**
     * Deletes the lesson identified by {@code lessonId}.
     *
     * @param lessonId the lesson to delete
     */
    void deleteLesson(UUID lessonId);
}
