package com.nsalazar.skill_track.review.infrastructure.in.web;

import com.nsalazar.skill_track.review.application.port.in.CreateReviewUseCase;
import com.nsalazar.skill_track.review.application.port.in.ListReviewsByCourseUseCase;
import com.nsalazar.skill_track.review.infrastructure.in.web.dto.CreateReviewRequest;
import com.nsalazar.skill_track.review.infrastructure.in.web.dto.ReviewResponse;
import com.nsalazar.skill_track.review.infrastructure.in.web.mapper.ReviewWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing review endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final ListReviewsByCourseUseCase listReviewsByCourseUseCase;
    private final ReviewWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/students/{studentId}/enrollments/{courseId}/review} — submits a review.
     */
    @PostMapping("/students/{studentId}/enrollments/{courseId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createReview(
            @PathVariable UUID studentId,
            @PathVariable UUID courseId,
            @Valid @RequestBody CreateReviewRequest request) {
        log.info("POST /api/v1/students/{}/enrollments/{}/review - rating: {}", studentId, courseId, request.rating());
        return mapper.toResponse(createReviewUseCase.createReview(mapper.toCommand(studentId, courseId, request)));
    }

    /**
     * Handles {@code GET /api/v1/courses/{courseId}/reviews} — lists all reviews for a course.
     */
    @GetMapping("/courses/{courseId}/reviews")
    public List<ReviewResponse> listReviews(@PathVariable UUID courseId) {
        log.info("GET /api/v1/courses/{}/reviews", courseId);
        return listReviewsByCourseUseCase.listReviewsByCourse(courseId)
                .stream().map(mapper::toResponse).toList();
    }
}
