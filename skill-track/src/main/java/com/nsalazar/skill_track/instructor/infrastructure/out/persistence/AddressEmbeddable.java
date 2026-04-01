package com.nsalazar.skill_track.instructor.infrastructure.out.persistence;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA embeddable representing a physical address.
 * Stored directly in the {@code instructors} table as three columns
 * ({@code street}, {@code city}, {@code country}) — no join, no extra table.
 *
 * <p>Mapped to the domain value object {@link com.nsalazar.skill_track.instructor.domain.Address}
 * via {@link com.nsalazar.skill_track.instructor.infrastructure.out.persistence.mapper.InstructorPersistenceMapper}.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressEmbeddable {

    private String street;
    private String city;
    private String country;
}
