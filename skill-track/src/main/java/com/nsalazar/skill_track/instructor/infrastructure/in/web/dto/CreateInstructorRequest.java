package com.nsalazar.skill_track.instructor.infrastructure.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for the create-instructor endpoint.
 * Required fields are validated via Bean Validation annotations before reaching the controller.
 *
 * @param firstName the instructor's first name (required)
 * @param lastName  the instructor's last name (required)
 * @param email     the instructor's email address (required, must be valid format)
 * @param bio       optional free-text biography
 */
public record CreateInstructorRequest(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @Email(message = "Must be a valid email") @NotBlank(message = "Email is required") String email,
        String bio
) {}
