package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.enrollment.application.port.in.ListEnrollmentsByStudentUseCase;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Application service for listing all enrollments of a student (paginated).
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListEnrollmentsByStudentService implements ListEnrollmentsByStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    /**
     * Validates the student exists, then returns a page of their enrollments.
     *
     * @param studentId the student id
     * @param pageable  pagination and sort parameters
     * @return a page of enrollments
     */
    @Override
    public Page<Enrollment> listEnrollmentsByStudent(UUID studentId, Pageable pageable) {
        log.info("Listing enrollments for studentId '{}' (page {})", studentId, pageable.getPageNumber());
        studentRepositoryPort.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        Page<Enrollment> page = enrollmentRepositoryPort.findByStudentId(studentId, pageable);
        log.info("Found {} enrollments for studentId '{}'", page.getTotalElements(), studentId);
        return page;
    }
}
