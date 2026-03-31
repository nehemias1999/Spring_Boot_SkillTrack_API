package com.nsalazar.skill_track.review.application.port.in;

import com.nsalazar.skill_track.review.domain.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Input port for the create-review use case.
 */
public interface CreateReviewUseCase {

    /**
     * Command carrying all data required to submit a review.
     *
     * @param studentId the id of the reviewing student
     * @param courseId  the id of the reviewed course
     * @param rating    star rating (1–5)
     * @param comment   optional free-text comment
     */
    record CreateReviewCommand(
            UUID studentId,
            UUID courseId,
            @NotNull @Min(1) @Max(5) int rating,
            String comment
    ) {}

    Review createReview(CreateReviewCommand command);
}
