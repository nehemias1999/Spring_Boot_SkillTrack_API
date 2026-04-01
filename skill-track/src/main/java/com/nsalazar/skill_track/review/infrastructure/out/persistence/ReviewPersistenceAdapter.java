package com.nsalazar.skill_track.review.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.domain.port.out.ReviewRepositoryPort;
import com.nsalazar.skill_track.review.infrastructure.out.persistence.mapper.ReviewPersistenceMapper;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;

    @Override
    public Review save(Review review) {
        log.debug("Saving review for studentId {} on courseId {}", review.studentId(), review.courseId());
        ReviewJpaEntity entity = mapper.toJpaEntity(review);
        entity.setStudent(entityManager.getReference(StudentJpaEntity.class, review.studentId()));
        entity.setCourse(entityManager.getReference(CourseJpaEntity.class, review.courseId()));
        return mapper.toDomain(reviewJpaRepository.save(entity));
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
