package com.nsalazar.skill_track.enrollment.domain;

/** Lifecycle status of a student's enrollment in a course. */
public enum EnrollmentStatus {
    /** The student is actively enrolled and working through the course. */
    ACTIVE,
    /** The student has completed all course material (progress = 100%). */
    COMPLETED,
    /** The student has voluntarily withdrawn from the course. */
    DROPPED
}
