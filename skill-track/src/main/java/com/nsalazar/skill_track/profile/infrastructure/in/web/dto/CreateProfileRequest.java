package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for creating a student profile.
 */
public record CreateProfileRequest(
        @NotBlank(message = "Bio is required") String bio,
        @NotBlank(message = "LinkedIn URL is required") String linkedInUrl,
        @NotBlank(message = "Phone number is required") String phoneNumber) {}
