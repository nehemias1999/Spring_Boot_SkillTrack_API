package com.nsalazar.skill_track.course.application.port.in;

import java.util.UUID;

/**
 * Inbound port for managing the self-referential prerequisite relationship between courses.
 * A prerequisite is a course that must be completed before a student can enrol in another course.
 *
 * <p>The relationship is persisted via {@code CoursePrerequisiteJpaEntity}, which uses an
 * {@code @EmbeddedId} composite primary key ({@link CoursePrerequisiteId}) consisting of
 * the two course UUIDs. This makes the join table strongly typed and avoids a surrogate key.
 */
public interface ManageCoursePrerequisiteUseCase {

    /**
     * Adds {@code prerequisiteId} as a prerequisite of {@code courseId}.
     *
     * @param courseId       the course that requires the prerequisite
     * @param prerequisiteId the course that must be completed first
     */
    void addPrerequisite(UUID courseId, UUID prerequisiteId);

    /**
     * Removes the prerequisite relationship between the two courses.
     *
     * @param courseId       the dependent course
     * @param prerequisiteId the prerequisite course to remove
     */
    void removePrerequisite(UUID courseId, UUID prerequisiteId);
}
