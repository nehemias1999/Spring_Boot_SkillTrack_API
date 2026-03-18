package com.nsalazar.skill_track.course.application.port.in;

import java.util.UUID;

/**
 * Inbound port for deleting a course by id.
 */
public interface DeleteCourseUseCase {
    /**
     * Deletes the course with the given id.
     *
     * @param id the course id
     */
    void deleteCourse(UUID id);
}
