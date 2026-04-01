package com.nsalazar.skill_track.instructor.infrastructure.in.web.dto;

/**
 * Response DTO for the instructor's physical address.
 * Fields may be {@code null} when not provided.
 */
public record AddressResponse(String street, String city, String country) {}
