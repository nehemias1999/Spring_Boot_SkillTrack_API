package com.nsalazar.skill_track.course.domain;

/** Publication status of a course. */
public enum CourseStatus {
    /** Course is not yet visible to students. */
    DRAFT,
    /** Course is live and students can enroll. */
    PUBLISHED,
    /** Course is no longer accepting new enrollments. */
    ARCHIVED
}
