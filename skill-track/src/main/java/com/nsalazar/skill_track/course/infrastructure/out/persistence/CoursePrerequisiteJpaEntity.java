package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Explicit join-table entity for the self-referential course-prerequisite relationship.
 * Using an explicit entity (rather than a plain {@code @ManyToMany @JoinTable}) allows
 * adding extra columns to the join table in the future (e.g. {@code addedAt}).
 *
 * <p>Primary key: composite {@link CoursePrerequisiteId} via {@code @EmbeddedId}.
 * Both FK columns ({@code course_id}, {@code prerequisite_id}) reference the same
 * {@code courses} table, making this a <b>self-referential many-to-many</b>.
 * {@code @MapsId} tells Hibernate which part of the composite PK each {@code @ManyToOne} drives.
 */
@Entity
@Table(name = "course_prerequisites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePrerequisiteJpaEntity {

    @EmbeddedId
    private CoursePrerequisiteId id;

    /**
     * The course that <em>has</em> the prerequisite (i.e. it is the dependent course).
     * Maps the {@code course_id} column inside the composite PK via {@code @MapsId}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private CourseJpaEntity course;

    /**
     * The course that must be completed first (i.e. it is the prerequisite course).
     * Maps the {@code prerequisite_id} column inside the composite PK via {@code @MapsId}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("prerequisiteId")
    @JoinColumn(name = "prerequisite_id")
    private CourseJpaEntity prerequisite;
}
