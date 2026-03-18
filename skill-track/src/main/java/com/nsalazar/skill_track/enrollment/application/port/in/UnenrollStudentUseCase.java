package com.nsalazar.skill_track.enrollment.application.port.in;

import java.util.UUID;

/**
 * Inbound port for unenrolling a student from a course.
 */
public interface UnenrollStudentUseCase {
    /**
     * Removes the enrollment for the given student and course.
     *
     * @param studentId the student id
     * @param courseId  the course id
     */
    void unenrollStudent(UUID studentId, UUID courseId);
}
