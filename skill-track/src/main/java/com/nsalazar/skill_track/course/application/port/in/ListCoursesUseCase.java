package com.nsalazar.skill_track.course.application.port.in;

import com.nsalazar.skill_track.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Inbound port for listing courses with pagination.
 */
public interface ListCoursesUseCase {
    /**
     * Returns a paginated list of all courses.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of courses
     */
    Page<Course> listCourses(Pageable pageable);
}
