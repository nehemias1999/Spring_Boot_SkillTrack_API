package com.nsalazar.skill_track.course.domain;

import java.math.BigDecimal;

/**
 * Domain record representing a course offered on the platform.
 *
 * @param id           unique identifier; {@code null} when not yet persisted
 * @param title        the course title
 * @param description  optional description of the course content
 * @param price        the enrolment price (must be positive)
 * @param instructorId the id of the instructor who owns this course
 */
public record Course(Long id, String title, String description, BigDecimal price, Long instructorId) {}
