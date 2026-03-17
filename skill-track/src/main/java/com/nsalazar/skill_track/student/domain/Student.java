package com.nsalazar.skill_track.student.domain;

/**
 * Domain record representing a student in the system.
 *
 * @param id        unique identifier; {@code null} when not yet persisted
 * @param firstName the student's first name
 * @param lastName  the student's last name
 * @param email     the student's unique email address
 */
public record Student(Long id, String firstName, String lastName, String email) {}
