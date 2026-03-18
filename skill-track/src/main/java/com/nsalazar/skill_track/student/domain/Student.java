package com.nsalazar.skill_track.student.domain;

import java.util.UUID;

/**
 * Domain record representing a student in the system.
 *
 * @param id        unique identifier; {@code null} when not yet persisted
 * @param firstName the student's first name
 * @param lastName  the student's last name
 * @param email     the student's unique email address
 */
public record Student(UUID id, String firstName, String lastName, String email) {}
