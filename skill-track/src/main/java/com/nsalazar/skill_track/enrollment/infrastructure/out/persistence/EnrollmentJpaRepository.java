package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
     * Returns all enrollments for a student with their student and course eagerly loaded via JOIN FETCH,
     * preventing the N+1 select problem when accessing enrollment details.
     *
     * @param studentId the id of the student
     * @return list of fully initialised enrollment entities
     */
    @Query("SELECT e FROM EnrollmentJpaEntity e JOIN FETCH e.student JOIN FETCH e.course WHERE e.student.id = :studentId")
    List<EnrollmentJpaEntity> findByStudentIdWithDetails(@Param("studentId") UUID studentId);

    /**
     * Returns a paginated list of enrollments for the given student.
     *
     * @param studentId the id of the student
     * @param pageable  pagination / sort parameters
     * @return a page of enrollment entities
     */
    Page<EnrollmentJpaEntity> findByStudentId(UUID studentId, Pageable pageable);

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
