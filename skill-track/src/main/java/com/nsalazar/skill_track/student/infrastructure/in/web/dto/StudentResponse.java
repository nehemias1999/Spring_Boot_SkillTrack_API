package com.nsalazar.skill_track.student.infrastructure.in.web.dto;

import java.util.UUID;

/**
 * Response DTO returned by student endpoints.
 *
 * @param id        the student's unique identifier
 * @param firstName the student's first name
 * @param lastName  the student's last name
 * @param email     the student's email address
 */
public record StudentResponse(UUID id, String firstName, String lastName, String email) {}
