package com.nsalazar.skill_track.profile.infrastructure.in.web.dto;

import java.util.Set;
import java.util.UUID;

/**
 * Response DTO returned by profile endpoints.
 *
 * @param id           the profile's unique identifier
 * @param studentId    the id of the owning student
 * @param bio          optional free-text biography
 * @param linkedInUrl  optional LinkedIn profile URL
 * @param phoneNumber  optional phone number
 * @param githubUrl    optional GitHub profile URL
 * @param portfolioUrl optional personal portfolio URL
 * @param avatarUrl    optional profile picture URL
 * @param skills       set of skill tags stored via {@code @ElementCollection}
 */
public record ProfileResponse(
        UUID id,
        UUID studentId,
        String bio,
        String linkedInUrl,
        String phoneNumber,
        String githubUrl,
        String portfolioUrl,
        String avatarUrl,
        Set<String> skills
) {}
