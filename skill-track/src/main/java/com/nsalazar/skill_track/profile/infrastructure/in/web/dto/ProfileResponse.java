package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

/**
 * Response DTO returned by profile endpoints.
 *
 * @param id          the profile's unique identifier
 * @param studentId   the id of the owning student
 * @param bio         optional free-text biography
 * @param linkedInUrl optional LinkedIn profile URL
 * @param phoneNumber optional phone number
 */
public record ProfileResponse(Long id, Long studentId, String bio, String linkedInUrl, String phoneNumber) {}
