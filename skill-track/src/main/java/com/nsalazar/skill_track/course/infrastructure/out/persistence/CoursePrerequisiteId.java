package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Composite primary key for the {@link CoursePrerequisiteJpaEntity} join table.
 * Demonstrates {@code @EmbeddedId} — a type-safe alternative to {@code @IdClass} for
 * composite primary keys. Both columns together form a unique constraint.
 *
 * <ul>
 *   <li>{@code courseId}      — the course that requires the prerequisite</li>
 *   <li>{@code prerequisiteId} — the course that must be completed first</li>
 * </ul>
 *
 * Must implement {@link Serializable} and override {@link #equals}/{@link #hashCode}
 * (provided by Lombok's {@code @EqualsAndHashCode}).
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CoursePrerequisiteId implements Serializable {

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "prerequisite_id")
    private UUID prerequisiteId;
}
