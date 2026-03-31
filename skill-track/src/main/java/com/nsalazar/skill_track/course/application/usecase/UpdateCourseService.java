package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.UpdateCourseUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(value = "courses", allEntries = true)
    public Course updateCourse(UpdateCourseCommand command) {
        log.info("Updating course with id '{}'", command.id());
        if (command.title() == null && command.description() == null && command.price() == null
                && command.category() == null && command.difficulty() == null
                && command.durationHours() == null && command.status() == null) {
            throw new BusinessValidationException("At least one field must be provided for update");
        }
        Course existing = courseRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + command.id()));

        Course updated = new Course(
                existing.id(),
                command.title() != null ? command.title() : existing.title(),
                command.description() != null ? command.description() : existing.description(),
                command.price() != null ? command.price() : existing.price(),
                existing.instructorId(),
                command.category() != null ? command.category() : existing.category(),
                command.difficulty() != null ? command.difficulty() : existing.difficulty(),
                command.durationHours() != null ? command.durationHours() : existing.durationHours(),
                command.status() != null ? command.status() : existing.status()
        );
        Course saved = courseRepositoryPort.save(updated);
        log.info("Course with id '{}' updated successfully", saved.id());
        return saved;
    }
}
