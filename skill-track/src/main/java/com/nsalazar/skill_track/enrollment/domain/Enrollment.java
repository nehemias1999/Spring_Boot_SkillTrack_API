package com.nsalazar.skill_track.enrollment.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain record representing a student's enrolment in a course.
 *
 * @param id                 unique identifier; {@code null} when not yet persisted
 * @param studentId          the id of the enrolled student
 * @param courseId           the id of the course the student enrolled in
 * @param enrolledAt         the timestamp at which the enrolment was created
 * @param status             the current lifecycle status of the enrollment
 * @param progressPercentage percentage of the course completed (0–100)
 * @param completedAt        the timestamp at which the student reached 100% progress; {@code null} until then
 */
public record Enrollment(
        UUID id,
        UUID studentId,
        UUID courseId,
        LocalDateTime enrolledAt,
        EnrollmentStatus status,
        int progressPercentage,
        LocalDateTime completedAt
) {}
