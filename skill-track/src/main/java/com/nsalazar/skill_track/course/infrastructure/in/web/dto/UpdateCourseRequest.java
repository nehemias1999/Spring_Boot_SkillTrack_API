package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import java.math.BigDecimal;

/**
 * Request DTO for partially updating a course. All fields are optional — null means keep existing value.
 */
public record UpdateCourseRequest(String title, String description, BigDecimal price) {}
