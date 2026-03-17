package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link EnrollmentJpaEntity}.
 * Provides standard CRUD operations and a derived duplicate-enrollment check.
 */
interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    /**
     * Checks whether an enrollment record exists for the given student and course pair.
     *
     * @param studentId the id of the student
     * @param courseId  the id of the course
     * @return {@code true} if the student is already enrolled in the course, {@code false} otherwise
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
