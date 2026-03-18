package com.nsalazar.skill_track.instructor.infrastructure.in.web.dto;

/**
 * Request DTO for partially updating an instructor. All fields are optional — null means keep existing value.
 */
public record UpdateInstructorRequest(String firstName, String lastName, String email, String bio) {}
