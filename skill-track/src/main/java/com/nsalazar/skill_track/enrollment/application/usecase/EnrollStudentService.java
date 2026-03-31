package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.enrollment.application.port.in.EnrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.EnrollmentStatus;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.event.StudentEnrolledEvent;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Application service that handles the enroll-student use case.
 * Validates that both the student and the course exist, and that the student
 * is not already enrolled, before creating the enrollment record.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EnrollStudentService implements EnrollStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;
    private final EnrollmentRepositoryPort enrollmentRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Enrolls a student in a course after validating all pre-conditions.
     *
     * @param studentId the id of the student to enroll
     * @param courseId  the id of the course to enroll in
     * @return the persisted {@link Enrollment} with its assigned id and timestamp
     * @throws ResourceNotFoundException   if the student or course does not exist
     * @throws BusinessValidationException if the student is already enrolled in the course
     */
    @Override
    public Enrollment enrollStudent(UUID studentId, UUID courseId) {
        log.info("Enrolling studentId {} in courseId {}", studentId, courseId);
        if (!studentRepositoryPort.findById(studentId).isPresent()) {
            log.warn("Student not found with id {}", studentId);
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        if (!courseRepositoryPort.findById(courseId).isPresent()) {
            log.warn("Course not found with id {}", courseId);
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
        if (enrollmentRepositoryPort.existsByStudentIdAndCourseId(studentId, courseId)) {
            log.warn("Student {} is already enrolled in course {}", studentId, courseId);
            throw new BusinessValidationException(
                    "Student " + studentId + " is already enrolled in course " + courseId);
        }
        Enrollment enrollment = new Enrollment(
                null, studentId, courseId, LocalDateTime.now(),
                EnrollmentStatus.ACTIVE, 0, null);
        Enrollment saved = enrollmentRepositoryPort.save(enrollment);
        log.info("Enrollment created successfully with id {}", saved.id());
        eventPublisher.publishEvent(new StudentEnrolledEvent(this, saved));
        return saved;
    }
}
