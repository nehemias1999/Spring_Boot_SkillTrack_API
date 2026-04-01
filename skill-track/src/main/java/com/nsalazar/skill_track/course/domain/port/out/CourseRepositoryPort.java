package com.nsalazar.skill_track.course.domain.port.out;

import com.nsalazar.skill_track.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Output port defining the persistence contract for {@link Course} entities.
 * Implementations are provided by the infrastructure layer.
 */
public interface CourseRepositoryPort {

    /**
     * Persists the given course and returns the saved state (including any generated id).
     *
     * @param course the course to save
     * @return the persisted course with its assigned id
     */
    Course save(Course course);

    /**
     * Looks up a course by its unique identifier.
     *
     * @param id the course id to search for
     * @return an {@link Optional} containing the course if found, or empty otherwise
     */
    Optional<Course> findById(UUID id);

    /**
     * Looks up a course by id with its instructor eagerly fetched via a single JOIN FETCH query,
     * avoiding the N+1 select problem that would occur when accessing the instructor lazily.
     *
     * @param id the course id to search for
     * @return an {@link Optional} containing the fully loaded course if found, or empty otherwise
     */
    Optional<Course> findByIdWithInstructor(UUID id);

    /**
     * Returns a paginated list of all courses.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of courses
     */
    Page<Course> findAll(Pageable pageable);

    /**
     * Deletes the course with the given id.
     *
     * @param id the course id
     */
    void deleteById(UUID id);
}
