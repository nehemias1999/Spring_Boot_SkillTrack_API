package com.nsalazar.skill_track.course.infrastructure.in.web.dto;

import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.domain.CourseStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Response DTO returned by course endpoints.
 *
 * @param id              the course's unique identifier
 * @param title           the course title
 * @param description     optional description of the course content
 * @param price           the enrolment price
 * @param instructorId    the id of the instructor who owns this course
 * @param category        subject area of the course
 * @param difficulty      difficulty level
 * @param durationHours   estimated duration in hours
 * @param status          publication status
 * @param keywords        free-form keyword strings ({@code @ElementCollection})
 * @param prerequisiteIds ids of prerequisite courses (self-referential many-to-many with composite PK)
 * @param tagNames        names of taxonomy tags ({@code @ManyToMany @JoinTable})
 */
public record CourseResponse(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        UUID instructorId,
        CourseCategory category,
        CourseDifficulty difficulty,
        Integer durationHours,
        CourseStatus status,
        Set<String> keywords,
        Set<UUID> prerequisiteIds,
        Set<String> tagNames
) {}
