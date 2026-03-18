package com.nsalazar.skill_track.course.application.port.in;

import com.nsalazar.skill_track.course.domain.Course;

import java.util.UUID;

/**
 * Input port for the get-course use case.
 * Defines the contract for retrieving a course by identifier.
 */
public interface GetCourseUseCase {

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the id of the course to retrieve
     * @return the {@link Course} matching the given id
     */
    Course getCourseById(UUID id);
}
