package com.nsalazar.skill_track.shared.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Async listener for enrollment-related domain events.
 * Each handler runs in a separate thread so the main transaction is not delayed.
 */
@Slf4j
@Component
public class EnrollmentEventListener {

    /**
     * Handles {@link StudentEnrolledEvent} — triggered after a student enrolls in a course.
     * Can be extended to send a welcome email, create a notification, etc.
     */
    @Async
    @EventListener
    public void onStudentEnrolled(StudentEnrolledEvent event) {
        log.info("[EVENT] Student {} enrolled in course {} (enrollmentId: {})",
                event.getEnrollment().studentId(),
                event.getEnrollment().courseId(),
                event.getEnrollment().id());
    }

    /**
     * Handles {@link EnrollmentCompletedEvent} — triggered when a student reaches 100% progress.
     * Can be extended to generate a certificate, send a congratulations email, etc.
     */
    @Async
    @EventListener
    public void onEnrollmentCompleted(EnrollmentCompletedEvent event) {
        log.info("[EVENT] Student {} completed course {} (enrollmentId: {}) — certificate can be generated.",
                event.getEnrollment().studentId(),
                event.getEnrollment().courseId(),
                event.getEnrollment().id());
    }
}
