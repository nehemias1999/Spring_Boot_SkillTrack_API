package com.nsalazar.skill_track.shared.event;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import org.springframework.context.ApplicationEvent;

/**
 * Published after a student successfully enrolls in a course.
 */
public class StudentEnrolledEvent extends ApplicationEvent {

    private final Enrollment enrollment;

    public StudentEnrolledEvent(Object source, Enrollment enrollment) {
        super(source);
        this.enrollment = enrollment;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }
}
