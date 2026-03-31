package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.enrollment.application.port.in.UpdateEnrollmentProgressUseCase;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.EnrollmentStatus;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.event.EnrollmentCompletedEvent;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Application service for updating the progress of an enrollment.
 * Automatically transitions status to {@code COMPLETED} when progress reaches 100%.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateEnrollmentProgressService implements UpdateEnrollmentProgressUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Enrollment updateProgress(UpdateEnrollmentProgressCommand command) {
        log.info("Updating progress for studentId '{}' in courseId '{}' to {}%",
                command.studentId(), command.courseId(), command.progressPercentage());

        Enrollment existing = enrollmentRepositoryPort
                .findByStudentIdAndCourseId(command.studentId(), command.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found for studentId: " + command.studentId()
                        + " and courseId: " + command.courseId()));

        if (existing.status() == EnrollmentStatus.DROPPED) {
            throw new BusinessValidationException("Cannot update progress of a dropped enrollment.");
        }

        boolean justCompleted = command.progressPercentage() == 100
                && existing.status() != EnrollmentStatus.COMPLETED;

        EnrollmentStatus newStatus = command.progressPercentage() == 100
                ? EnrollmentStatus.COMPLETED
                : EnrollmentStatus.ACTIVE;

        LocalDateTime completedAt = newStatus == EnrollmentStatus.COMPLETED
                ? (existing.completedAt() != null ? existing.completedAt() : LocalDateTime.now())
                : existing.completedAt();

        Enrollment updated = new Enrollment(
                existing.id(),
                existing.studentId(),
                existing.courseId(),
                existing.enrolledAt(),
                newStatus,
                command.progressPercentage(),
                completedAt
        );

        Enrollment saved = enrollmentRepositoryPort.save(updated);
        log.info("Progress updated for enrollment id '{}'", saved.id());

        if (justCompleted) {
            eventPublisher.publishEvent(new EnrollmentCompletedEvent(this, saved));
        }

        return saved;
    }
}
