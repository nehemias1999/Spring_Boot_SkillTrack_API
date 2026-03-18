package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.UpdateCourseUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for partially updating a course.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateCourseService implements UpdateCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    /**
     * Fetches the existing course and applies only the non-null fields from the command.
     *
     * @param command the update command
     * @return the updated course
     */
    @Override
    public Course updateCourse(UpdateCourseCommand command) {
        log.info("Updating course with id '{}'", command.id());
        Course existing = courseRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + command.id()));

        Course updated = new Course(
                existing.id(),
                command.title() != null ? command.title() : existing.title(),
                command.description() != null ? command.description() : existing.description(),
                command.price() != null ? command.price() : existing.price(),
                existing.instructorId()
        );
        Course saved = courseRepositoryPort.save(updated);
        log.info("Course with id '{}' updated successfully", saved.id());
        return saved;
    }
}
