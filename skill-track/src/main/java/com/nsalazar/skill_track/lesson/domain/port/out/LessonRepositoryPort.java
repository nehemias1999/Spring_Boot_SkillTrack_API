package com.nsalazar.skill_track.lesson.domain.port.out;

import com.nsalazar.skill_track.lesson.domain.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Outbound port defining the persistence contract for {@link Lesson} entities.
 */
public interface LessonRepositoryPort {

    /**
     * Persists a lesson and returns the saved state with its generated id.
     *
     * @param lesson the lesson to save
     * @return the saved lesson
     */
    Lesson save(Lesson lesson);

    /**
     * Looks up a lesson by its unique identifier.
     *
     * @param id the lesson id
     * @return an {@link Optional} containing the lesson if found, or empty otherwise
     */
    Optional<Lesson> findById(UUID id);

    /**
     * Returns all lessons for a given course, ordered by their {@code position}.
     *
     * @param courseId the course id
     * @return lessons ordered by position ascending
     */
    List<Lesson> findByCourseIdOrderByPosition(UUID courseId);

    /**
     * Deletes a lesson by its unique identifier.
     *
     * @param id the lesson id
     */
    void deleteById(UUID id);
}
