package com.nsalazar.skill_track.instructor.domain;

/**
 * Domain record representing an instructor in the system.
 *
 * @param id        unique identifier; {@code null} when not yet persisted
 * @param firstName the instructor's first name
 * @param lastName  the instructor's last name
 * @param email     the instructor's unique email address
 * @param bio       optional free-text biography
 */
public record Instructor(Long id, String firstName, String lastName, String email, String bio) {}
