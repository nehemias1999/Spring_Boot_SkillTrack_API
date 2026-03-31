package com.nsalazar.skill_track.review.infrastructure.in.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO returned by review endpoints.
 *
 * @param id        the review's unique identifier
 * @param studentId the id of the reviewing student
 * @param courseId  the id of the reviewed course
 * @param rating    star rating (1–5)
 * @param comment   optional free-text comment
 * @param createdAt timestamp when the review was submitted
 */
public record ReviewResponse(
        UUID id,
        UUID studentId,
        UUID courseId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {}
