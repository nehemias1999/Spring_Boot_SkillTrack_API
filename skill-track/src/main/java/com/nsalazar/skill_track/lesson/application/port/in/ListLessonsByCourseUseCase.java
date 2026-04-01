package com.nsalazar.skill_track.lesson.application.port.in;

import com.nsalazar.skill_track.lesson.domain.Lesson;

import java.util.List;
import java.util.UUID;

/**
 * Inbound port for listing all lessons that belong to a given course.
 */
public interface ListLessonsByCourseUseCase {

    /**
     * Returns all lessons for the specified course, ordered by their position.
     *
     * @param courseId the course id
     * @return lessons ordered by {@code position} ascending
     */
    List<Lesson> listLessonsByCourse(UUID courseId);
}
