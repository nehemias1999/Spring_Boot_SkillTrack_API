package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.GetCourseUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service that handles the get-course use case.
 * Retrieves an existing course by id, throwing a not-found exception when absent.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCourseService implements GetCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the id of the course to retrieve
     * @return the matching {@link Course}
     * @throws ResourceNotFoundException if no course exists with the given id
     */
    @Override
    public Course getCourseById(UUID id) {
        log.info("Fetching course with id {}", id);
        Course course = courseRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found with id {}", id);
                    return new ResourceNotFoundException("Course not found with id: " + id);
                });
        log.info("Course with id {} retrieved successfully", id);
        return course;
    }
}
