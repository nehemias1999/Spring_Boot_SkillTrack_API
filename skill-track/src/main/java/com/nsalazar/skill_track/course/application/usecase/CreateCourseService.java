package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service that handles the create-course use case.
 * Validates that the referenced instructor exists before persisting a new course.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateCourseService implements CreateCourseUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;

    /**
     * Creates a new course under the specified instructor.
     *
     * @param command the validated command containing course details and the owning instructor id
     * @return the persisted {@link Course} with its assigned id
     * @throws ResourceNotFoundException if no instructor exists with the given id
     */
    @Override
    public Course createCourse(CreateCourseCommand command) {
        log.info("Creating course '{}' for instructorId {}", command.title(), command.instructorId());
        if (!instructorRepositoryPort.findById(command.instructorId()).isPresent()) {
            log.warn("Instructor not found with id {}", command.instructorId());
            throw new ResourceNotFoundException("Instructor not found with id: " + command.instructorId());
        }
        Course course = new Course(null, command.title(), command.description(),
                command.price(), command.instructorId());
        Course saved = courseRepositoryPort.save(course);
        log.info("Course created successfully with id {}", saved.id());
        return saved;
    }
}
