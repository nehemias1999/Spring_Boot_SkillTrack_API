package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

/**
 * Request DTO for the create-profile endpoint.
 * All fields are optional; the student id is supplied via the URL path variable.
 *
 * @param bio         optional free-text biography
 * @param linkedInUrl optional LinkedIn profile URL
 * @param phoneNumber optional phone number
 */
public record CreateProfileRequest(String bio, String linkedInUrl, String phoneNumber) {}
