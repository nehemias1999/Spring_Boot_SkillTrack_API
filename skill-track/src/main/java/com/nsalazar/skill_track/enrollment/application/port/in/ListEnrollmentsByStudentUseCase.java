package com.nsalazar.skill_track.enrollment.application.port.in;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Inbound port for listing all enrollments of a student.
 * Returns a paginated result to support large enrollment histories.
 */
public interface ListEnrollmentsByStudentUseCase {

    /**
     * Returns a page of enrollments for the given student.
     *
     * @param studentId the student id
     * @param pageable  pagination and sort parameters (e.g. {@code page=0&size=10&sort=enrolledAt,desc})
     * @return a page of enrollments
     */
    Page<Enrollment> listEnrollmentsByStudent(UUID studentId, Pageable pageable);
}
