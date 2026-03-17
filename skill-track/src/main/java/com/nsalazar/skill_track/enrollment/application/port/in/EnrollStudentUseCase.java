package com.nsalazar.skill_track.enrollment.application.port.in;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;

/**
 * Input port for the enroll-student use case.
 * Defines the contract for enrolling a student in a course.
 */
public interface EnrollStudentUseCase {

    /**
     * Enrolls the specified student in the specified course.
     *
     * @param studentId the id of the student to enroll
     * @param courseId  the id of the course to enroll in
     * @return the newly created {@link Enrollment} with its assigned id and timestamp
     */
    Enrollment enrollStudent(Long studentId, Long courseId);
}
