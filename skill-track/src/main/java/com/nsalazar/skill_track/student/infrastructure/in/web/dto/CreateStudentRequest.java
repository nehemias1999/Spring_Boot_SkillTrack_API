package com.nsalazar.skill_track.student.infrastructure.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for the create-student endpoint.
 * All fields are validated via Bean Validation annotations before reaching the controller.
 *
 * @param firstName the student's first name (required)
 * @param lastName  the student's last name (required)
 * @param email     the student's email address (required, must be valid format)
 */
public record CreateStudentRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @Email(message = "Must be a valid email") @NotBlank(message = "Email is required") String email
) {}
