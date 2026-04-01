package com.nsalazar.skill_track.profile.domain;

import java.util.Set;
import java.util.UUID;

/**
 * Domain record representing an extended profile attached to a student.
 *
 * @param id           unique identifier; {@code null} when not yet persisted
 * @param studentId    the id of the owning student
 * @param bio          optional free-text biography
 * @param linkedInUrl  optional LinkedIn profile URL
 * @param phoneNumber  optional phone number
 * @param githubUrl    optional GitHub profile URL
 * @param portfolioUrl optional personal portfolio / website URL
 * @param avatarUrl    optional URL pointing to the student's profile picture
 * @param skills       free-form skill tags (e.g. "Java", "Spring Boot") stored in a separate collection
 *                     table via {@code @ElementCollection}; may be empty but never {@code null}
 * @param version      optimistic-locking token managed by JPA; {@code null} for new entities
 */
public record Profile(
        UUID id,
        UUID studentId,
        String bio,
        String linkedInUrl,
        String phoneNumber,
        String githubUrl,
        String portfolioUrl,
        String avatarUrl,
        Set<String> skills,
        Long version
) {}
