package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.enrollment.application.port.in.UnenrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for unenrolling a student from a course.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UnenrollStudentService implements UnenrollStudentUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    /**
     * Validates the enrollment exists then removes it.
     *
     * @param studentId the student id
     * @param courseId  the course id
     */
    @Override
    public void unenrollStudent(UUID studentId, UUID courseId) {
        log.info("Unenrolling studentId '{}' from courseId '{}'", studentId, courseId);
        if (!enrollmentRepositoryPort.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new ResourceNotFoundException(
                    "Enrollment not found for studentId: " + studentId + " and courseId: " + courseId);
        }
        enrollmentRepositoryPort.deleteByStudentIdAndCourseId(studentId, courseId);
        log.info("Student '{}' unenrolled from course '{}' successfully", studentId, courseId);
    }
}
