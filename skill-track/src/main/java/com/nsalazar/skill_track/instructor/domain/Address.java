package com.nsalazar.skill_track.instructor.domain;

/**
 * Value object representing a physical address.
 * Used as an embedded attribute in {@link Instructor} — there is no separate address table;
 * all columns are stored inside the {@code instructors} table via JPA {@code @Embedded}.
 *
 * @param street  street line (may be {@code null} if not provided)
 * @param city    city name (may be {@code null} if not provided)
 * @param country country name (may be {@code null} if not provided)
 */
public record Address(String street, String city, String country) {}
