package com.nsalazar.skill_track.review.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain record representing a student's review of a course.
 *
 * @param id        unique identifier; {@code null} when not yet persisted
 * @param studentId the id of the student who left the review
 * @param courseId  the id of the reviewed course
 * @param rating    star rating from 1 (worst) to 5 (best)
 * @param comment   optional free-text comment
 * @param createdAt timestamp when the review was submitted
 */
public record Review(
        UUID id,
        UUID studentId,
        UUID courseId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {}
