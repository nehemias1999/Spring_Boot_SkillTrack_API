package com.nsalazar.skill_track.course.application.port.in;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.domain.CourseStatus;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Inbound port for partially updating a course.
 */
public interface UpdateCourseUseCase {
    /**
     * Command for updating a course. All fields are optional (null = keep existing value).
     */
    record UpdateCourseCommand(
            UUID id,
            String title,
            String description,
            BigDecimal price,
            CourseCategory category,
            CourseDifficulty difficulty,
            Integer durationHours,
            CourseStatus status
    ) {}

    /**
     * Updates the course with the provided non-null fields.
     *
     * @param command the update command
     * @return the updated course
     */
    Course updateCourse(UpdateCourseCommand command);
}
