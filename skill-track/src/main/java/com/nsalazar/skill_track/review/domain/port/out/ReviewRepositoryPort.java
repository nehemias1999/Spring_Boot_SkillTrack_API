package com.nsalazar.skill_track.review.domain.port.out;

import com.nsalazar.skill_track.review.domain.Review;

import java.util.List;
import java.util.UUID;

/**
 * Output port defining the persistence contract for {@link Review} entities.
 */
public interface ReviewRepositoryPort {

    Review save(Review review);

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    List<Review> findByCourseId(UUID courseId);
}
