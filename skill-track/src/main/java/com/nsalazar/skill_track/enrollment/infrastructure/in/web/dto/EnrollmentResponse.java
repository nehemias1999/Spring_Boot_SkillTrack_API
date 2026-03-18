package com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO returned by enrollment endpoints.
 *
 * @param id         the enrollment's unique identifier
 * @param studentId  the id of the enrolled student
 * @param courseId   the id of the course
 * @param enrolledAt the timestamp at which the enrollment was created
 */
public record EnrollmentResponse(UUID id, UUID studentId, UUID courseId, LocalDateTime enrolledAt) {}
