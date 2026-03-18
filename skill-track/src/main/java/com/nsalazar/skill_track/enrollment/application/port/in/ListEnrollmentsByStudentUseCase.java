package com.nsalazar.skill_track.enrollment.application.port.in;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import java.util.List;
import java.util.UUID;

/**
 * Inbound port for listing all enrollments of a student.
 */
public interface ListEnrollmentsByStudentUseCase {
    /**
     * Returns all enrollments for the given student.
     *
     * @param studentId the student id
     * @return list of enrollments
     */
    List<Enrollment> listEnrollmentsByStudent(UUID studentId);
}
