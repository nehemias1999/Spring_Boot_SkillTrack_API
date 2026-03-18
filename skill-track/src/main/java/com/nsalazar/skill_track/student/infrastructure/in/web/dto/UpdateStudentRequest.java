package com.nsalazar.skill_track.student.infrastructure.in.web.dto;

/**
 * Request DTO for partially updating a student. All fields are optional — null means keep existing value.
 */
public record UpdateStudentRequest(String firstName, String lastName, String email) {}
