package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

/**
 * Request DTO for partially updating a profile. All fields are optional — null means keep existing value.
 */
public record UpdateProfileRequest(
        String bio,
        String linkedInUrl,
        String phoneNumber,
        String githubUrl,
        String portfolioUrl,
        String avatarUrl
) {}
