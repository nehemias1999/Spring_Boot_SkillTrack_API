package com.nsalazar.skill_track.instructor.infrastructure.in.web.dto;

import java.util.UUID;

/**
 * Response DTO returned by instructor endpoints.
 *
 * @param id        the instructor's unique identifier
 * @param firstName the instructor's first name
 * @param lastName  the instructor's last name
 * @param email     the instructor's email address
 * @param bio       optional free-text biography
 */
public record InstructorResponse(UUID id, String firstName, String lastName, String email, String bio) {}
