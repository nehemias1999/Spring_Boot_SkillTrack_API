package com.nsalazar.skill_track.profile.domain;

import java.util.UUID;

/**
 * Domain record representing an extended profile attached to a student.
 *
 * @param id          unique identifier; {@code null} when not yet persisted
 * @param studentId   the id of the owning student
 * @param bio         optional free-text biography
 * @param linkedInUrl optional LinkedIn profile URL
 * @param phoneNumber optional phone number
 */
public record Profile(UUID id, UUID studentId, String bio, String linkedInUrl, String phoneNumber) {}
