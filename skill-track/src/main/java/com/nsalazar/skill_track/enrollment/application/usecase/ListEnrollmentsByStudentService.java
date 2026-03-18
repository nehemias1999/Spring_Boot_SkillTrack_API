package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.enrollment.application.port.in.ListEnrollmentsByStudentUseCase;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Application service for listing all enrollments of a student.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListEnrollmentsByStudentService implements ListEnrollmentsByStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    /**
     * Validates the student exists, then returns all their enrollments.
     *
     * @param studentId the student id
     * @return list of enrollments
     */
    @Override
    public List<Enrollment> listEnrollmentsByStudent(UUID studentId) {
        log.info("Listing enrollments for studentId '{}'", studentId);
        studentRepositoryPort.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        List<Enrollment> enrollments = enrollmentRepositoryPort.findByStudentId(studentId);
        log.info("Found {} enrollments for studentId '{}'", enrollments.size(), studentId);
        return enrollments;
    }
}
