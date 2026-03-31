package com.nsalazar.skill_track.review.application.usecase;

import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.review.application.port.in.ListReviewsByCourseUseCase;
import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.domain.port.out.ReviewRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Application service for listing all reviews of a course.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListReviewsByCourseService implements ListReviewsByCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;
    private final ReviewRepositoryPort reviewRepositoryPort;

    @Override
    public List<Review> listReviewsByCourse(UUID courseId) {
        log.info("Listing reviews for courseId '{}'", courseId);
        courseRepositoryPort.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
        List<Review> reviews = reviewRepositoryPort.findByCourseId(courseId);
        log.info("Found {} review(s) for courseId '{}'", reviews.size(), courseId);
        return reviews;
    }
}
