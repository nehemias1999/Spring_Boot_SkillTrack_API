package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link EnrollmentJpaEntity}.
 * Provides standard CRUD operations and a derived duplicate-enrollment check.
 */
interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {

    /**
     * Checks whether an enrollment record exists for the given student and course pair.
     *
     * @param studentId the id of the student
     * @param courseId  the id of the course
     * @return {@code true} if the student is already enrolled in the course, {@code false} otherwise
     */
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    /**
     * Returns all enrollment records for the given student.
     *
     * @param studentId the id of the student
     * @return list of enrollment entities
     */
    List<EnrollmentJpaEntity> findByStudentId(UUID studentId);

    /**
     * Deletes the enrollment record for the given student and course pair.
     *
     * @param studentId the id of the student
     * @param courseId  the id of the course
     */
    void deleteByStudentIdAndCourseId(UUID studentId, UUID courseId);

    /**
     * Returns the enrollment for the given student and course, if it exists.
     *
     * @param studentId the id of the student
     * @param courseId  the id of the course
     * @return an {@link java.util.Optional} containing the enrollment entity if found
     */
    java.util.Optional<EnrollmentJpaEntity> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
