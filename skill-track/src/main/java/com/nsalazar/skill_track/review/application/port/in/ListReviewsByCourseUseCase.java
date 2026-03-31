package com.nsalazar.skill_track.review.application.port.in;

import com.nsalazar.skill_track.review.domain.Review;

import java.util.List;
import java.util.UUID;

/**
 * Input port for listing all reviews of a course.
 */
public interface ListReviewsByCourseUseCase {

    List<Review> listReviewsByCourse(UUID courseId);
}
