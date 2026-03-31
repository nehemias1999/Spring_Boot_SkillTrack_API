package com.nsalazar.skill_track.review.infrastructure.out.persistence;

import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.domain.port.out.ReviewRepositoryPort;
import com.nsalazar.skill_track.review.infrastructure.out.persistence.mapper.ReviewPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Persistence adapter implementing {@link ReviewRepositoryPort} via Spring Data JPA.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewRepositoryPort {

    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewPersistenceMapper mapper;

    @Override
    public Review save(Review review) {
        log.debug("Saving review for studentId {} on courseId {}", review.studentId(), review.courseId());
        return mapper.toDomain(reviewJpaRepository.save(mapper.toJpaEntity(review)));
    }

    @Override
    public boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        return reviewJpaRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<Review> findByCourseId(UUID courseId) {
        log.debug("Finding reviews for courseId {}", courseId);
        return reviewJpaRepository.findByCourseId(courseId).stream().map(mapper::toDomain).toList();
    }
}
