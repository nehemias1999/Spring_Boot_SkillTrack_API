package com.nsalazar.skill_track.instructor.infrastructure.in.web.dto;

/**
 * Request DTO for the instructor's physical address.
 * All fields are optional — supply only the parts you want to set.
 */
public record AddressRequest(String street, String city, String country) {}
