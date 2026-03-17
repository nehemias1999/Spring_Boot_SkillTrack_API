package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import java.math.BigDecimal;

/**
 * Response DTO returned by course endpoints.
 *
 * @param id           the course's unique identifier
 * @param title        the course title
 * @param description  optional description of the course content
 * @param price        the enrolment price
 * @param instructorId the id of the instructor who owns this course
 */
public record CourseResponse(Long id, String title, String description, BigDecimal price, Long instructorId) {}
