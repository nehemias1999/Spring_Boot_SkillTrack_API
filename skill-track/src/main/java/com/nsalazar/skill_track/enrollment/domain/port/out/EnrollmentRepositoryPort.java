package com.nsalazar.skill_track.enrollment.domain.port.out;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;

/**
 * Output port defining the persistence contract for {@link Enrollment} entities.
 * Implementations are provided by the infrastructure layer.
 */
public interface EnrollmentRepositoryPort {

    /**
     * Persists the given enrollment and returns the saved state (including any generated id).
     *
     * @param enrollment the enrollment to save
     * @return the persisted enrollment with its assigned id
     */
    Enrollment save(Enrollment enrollment);

    /**
     * Checks whether an enrollment already exists for the given student and course pair.
     *
     * @param studentId the student id to check
     * @param courseId  the course id to check
     * @return {@code true} if the student is already enrolled in the course, {@code false} otherwise
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
