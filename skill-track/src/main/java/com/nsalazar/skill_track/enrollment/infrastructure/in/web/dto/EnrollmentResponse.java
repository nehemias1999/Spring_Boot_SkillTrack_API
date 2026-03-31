package com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto;

import com.nsalazar.skill_track.enrollment.domain.EnrollmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO returned by enrollment endpoints.
 *
 * @param id                 the enrollment's unique identifier
 * @param studentId          the id of the enrolled student
 * @param courseId           the id of the course
 * @param enrolledAt         the timestamp at which the enrollment was created
 * @param status             the current lifecycle status
 * @param progressPercentage percentage of the course completed (0–100)
 * @param completedAt        timestamp when the student finished the course; null until then
 */
public record EnrollmentResponse(
        UUID id,
        UUID studentId,
        UUID courseId,
        LocalDateTime enrolledAt,
        EnrollmentStatus status,
        int progressPercentage,
        LocalDateTime completedAt
) {}
