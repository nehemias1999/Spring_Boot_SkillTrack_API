package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA entity for taxonomy tags.
 * Demonstrates a proper {@code @ManyToMany} relationship: tags are independent entities
 * with their own lifecycle, reusable across many courses, linked via the {@code course_tags}
 * join table (managed by {@link CourseJpaEntity}).
 *
 * <p>Contrast with {@code @ElementCollection} (used for {@code keywords} on Course), which
 * stores simple strings without identity — tags have their own {@code id} and {@code name}
 * and can be queried independently.
 */
@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Back-reference to all courses that carry this tag.
     * The owning side of the relationship lives on {@link CourseJpaEntity#tags}.
     */
    @ManyToMany(mappedBy = "tags")
    private Set<CourseJpaEntity> courses = new HashSet<>();
}
