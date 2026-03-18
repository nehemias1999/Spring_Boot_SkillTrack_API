package com.nsalazar.skill_track.course.application.port.in;

import com.nsalazar.skill_track.course.domain.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Input port for the create-course use case.
 * Defines the command record and the single method that drives course creation.
 */
public interface CreateCourseUseCase {

    /**
     * Immutable command object carrying the data required to create a new course.
     *
     * @param instructorId the id of the instructor who will own the course
     * @param title        the course title (must not be blank)
     * @param description  optional description of the course content
     * @param price        the enrolment price (must be present and positive)
     */
    record CreateCourseCommand(
            UUID instructorId,
            @NotBlank String title,
            String description,
            @NotNull @Positive BigDecimal price
    ) {}

    /**
     * Creates a new course from the supplied command.
     *
     * @param command the validated command containing course details
     * @return the newly created {@link Course} with its assigned id
     */
    Course createCourse(CreateCourseCommand command);
}
