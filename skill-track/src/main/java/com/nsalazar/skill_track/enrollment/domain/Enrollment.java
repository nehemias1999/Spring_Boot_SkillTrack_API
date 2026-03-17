package com.nsalazar.skill_track.enrollment.domain;

import java.time.LocalDateTime;

/**
 * Domain record representing a student's enrolment in a course.
 *
 * @param id         unique identifier; {@code null} when not yet persisted
 * @param studentId  the id of the enrolled student
 * @param courseId   the id of the course the student enrolled in
 * @param enrolledAt the timestamp at which the enrolment was created
 */
public record Enrollment(Long id, Long studentId, Long courseId, LocalDateTime enrolledAt) {}
