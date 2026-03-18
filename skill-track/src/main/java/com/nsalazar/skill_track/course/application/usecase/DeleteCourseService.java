package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.DeleteCourseUseCase;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for deleting a course.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeleteCourseService implements DeleteCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    /**
     * Verifies the course exists then deletes it.
     *
     * @param id the course id
     */
    @Override
    public void deleteCourse(UUID id) {
        log.info("Deleting course with id '{}'", id);
        courseRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        courseRepositoryPort.deleteById(id);
        log.info("Course with id '{}' deleted successfully", id);
    }
}
