package com.nsalazar.skill_track.review.application.usecase;

import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.review.application.port.in.CreateReviewUseCase;
import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.domain.port.out.ReviewRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for submitting a course review.
 * Business rules:
 * <ul>
 *   <li>The course must exist.</li>
 *   <li>The student must be enrolled in the course.</li>
 *   <li>Each student can only leave one review per course.</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateReviewService implements CreateReviewUseCase {

    private final CourseRepositoryPort courseRepositoryPort;
    private final EnrollmentRepositoryPort enrollmentRepositoryPort;
    private final ReviewRepositoryPort reviewRepositoryPort;

    @Override
    public Review createReview(CreateReviewCommand command) {
        log.info("Creating review for studentId '{}' on courseId '{}'",
                command.studentId(), command.courseId());

        courseRepositoryPort.findById(command.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + command.courseId()));

        if (!enrollmentRepositoryPort.existsByStudentIdAndCourseId(command.studentId(), command.courseId())) {
            throw new BusinessValidationException(
                    "Student " + command.studentId() + " is not enrolled in course " + command.courseId()
                    + ". Enrollment is required to leave a review.");
        }

        if (reviewRepositoryPort.existsByStudentIdAndCourseId(command.studentId(), command.courseId())) {
            throw new BusinessValidationException(
                    "Student " + command.studentId() + " has already reviewed course " + command.courseId());
        }

        // createdAt is null here; it is populated by JPA auditing after persist and returned from toDomain()
        Review review = new Review(null, command.studentId(), command.courseId(),
                command.rating(), command.comment(), null);
        Review saved = reviewRepositoryPort.save(review);
        log.info("Review created with id '{}'", saved.id());
        return saved;
    }
}
