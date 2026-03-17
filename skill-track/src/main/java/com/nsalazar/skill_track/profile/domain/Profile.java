package com.nsalazar.skill_track.profile.domain;

/**
 * Domain record representing an extended profile attached to a student.
 *
 * @param id          unique identifier; {@code null} when not yet persisted
 * @param studentId   the id of the owning student
 * @param bio         optional free-text biography
 * @param linkedInUrl optional LinkedIn profile URL
 * @param phoneNumber optional phone number
 */
public record Profile(Long id, Long studentId, String bio, String linkedInUrl, String phoneNumber) {}
