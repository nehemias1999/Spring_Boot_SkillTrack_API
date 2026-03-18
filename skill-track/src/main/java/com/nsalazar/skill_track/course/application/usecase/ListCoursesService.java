package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.ListCoursesUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for listing courses with pagination.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListCoursesService implements ListCoursesUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    /**
     * Returns a paginated list of all courses.
     *
     * @param pageable pagination parameters
     * @return a page of courses
     */
    @Override
    public Page<Course> listCourses(Pageable pageable) {
        log.info("Listing courses with pageable: {}", pageable);
        return courseRepositoryPort.findAll(pageable);
    }
}
