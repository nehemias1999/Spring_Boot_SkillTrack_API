package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

import java.util.Set;

/**
 * Request DTO for partially updating a profile. All fields are optional — null means keep existing value.
 * Providing an empty {@code skills} set explicitly clears all skills.
 */
public record UpdateProfileRequest(
        String bio,
        String linkedInUrl,
        String phoneNumber,
        String githubUrl,
        String portfolioUrl,
        String avatarUrl,
        Set<String> skills
) {}
