package com.nsalazar.skill_track.instructor.domain;

import java.util.UUID;

/**
 * Domain record representing an instructor in the system.
 *
 * @param id        unique identifier; {@code null} when not yet persisted
 * @param firstName the instructor's first name
 * @param lastName  the instructor's last name
 * @param email     the instructor's unique email address
 * @param bio       optional free-text biography
 * @param address   optional physical address; stored via JPA {@code @Embedded} (no separate table)
 * @param version   optimistic-locking token managed by JPA; {@code null} for new entities
 */
public record Instructor(UUID id, String firstName, String lastName, String email, String bio,
                         Address address, Long version) {}
