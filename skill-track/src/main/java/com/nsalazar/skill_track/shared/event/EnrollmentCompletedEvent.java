package com.nsalazar.skill_track.shared.event;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import org.springframework.context.ApplicationEvent;

/**
 * Published when a student's enrollment progress reaches 100% and is marked as COMPLETED.
 */
public class EnrollmentCompletedEvent extends ApplicationEvent {

    private final Enrollment enrollment;

    public EnrollmentCompletedEvent(Object source, Enrollment enrollment) {
        super(source);
        this.enrollment = enrollment;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }
}
