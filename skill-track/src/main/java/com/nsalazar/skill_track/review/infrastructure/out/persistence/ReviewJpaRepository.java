package com.nsalazar.skill_track.review.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link ReviewJpaEntity}.
 */
interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, UUID> {

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    List<ReviewJpaEntity> findByCourseId(UUID courseId);

}
