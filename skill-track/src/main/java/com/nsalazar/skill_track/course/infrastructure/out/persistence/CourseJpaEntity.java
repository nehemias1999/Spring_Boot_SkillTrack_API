package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.domain.CourseStatus;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import com.nsalazar.skill_track.shared.persistence.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA entity that maps to the {@code courses} database table.
 * Holds a many-to-one relationship with {@link InstructorJpaEntity}.
 * Used exclusively within the persistence layer; not exposed to the domain.
 *
 * <p>Demonstrates multiple JPA collection mapping strategies:
 * <ul>
 *   <li>{@code keywords} — {@code @ElementCollection}: simplest form, stores scalar strings
 *       in a dedicated collection table ({@code course_keywords}) with no entity identity.</li>
 *   <li>{@code prerequisites} — self-referential one-to-many via the explicit join entity
 *       {@link CoursePrerequisiteJpaEntity} (which uses an {@code @EmbeddedId} composite PK).</li>
 *   <li>{@code tags} — {@code @ManyToMany @JoinTable}: proper many-to-many with entity identity;
 *       tags are independent, reusable objects stored in their own {@code tags} table.</li>
 * </ul>
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseJpaEntity extends Auditable {

    /** Auto-generated UUID primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Optimistic locking version field. Hibernate increments this on every update;
     * a stale-version write throws {@link org.springframework.orm.ObjectOptimisticLockingFailureException}.
     */
    @Version
    private Long version;

    /** The course title. */
    @Column(nullable = false)
    private String title;

    /** Optional description of the course content. */
    private String description;

    /** The enrolment price with precision 10 and scale 2. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** The instructor who owns this course. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private InstructorJpaEntity instructor;

    /** Subject area of the course. */
    @Enumerated(EnumType.STRING)
    private CourseCategory category;

    /** Difficulty level of the course. */
    @Enumerated(EnumType.STRING)
    private CourseDifficulty difficulty;

    /** Estimated total duration in hours. */
    private Integer durationHours;

    /** Publication status of the course. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status;

    /**
     * Free-form keyword strings stored in the {@code course_keywords} collection table.
     * Uses {@code @ElementCollection}: simplest JPA collection mapping — no entity identity,
     * no join entity, just scalar values in a dedicated table with a FK back to this course.
     * Best for simple, value-type collections that do not need to be queried independently.
     */
    @ElementCollection
    @CollectionTable(name = "course_keywords", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();

    /**
     * Self-referential prerequisites: courses that must be completed before enrolling here.
     * Uses an <b>explicit join entity</b> ({@link CoursePrerequisiteJpaEntity}) with a
     * composite primary key ({@link CoursePrerequisiteId} via {@code @EmbeddedId}).
     * This pattern allows adding extra columns to the join table without refactoring later.
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CoursePrerequisiteJpaEntity> prerequisites = new HashSet<>();

    /**
     * Taxonomy tags for this course, linked via the {@code course_tags} join table.
     * Uses {@code @ManyToMany @JoinTable}: tags are independent entities with their own identity
     * ({@link TagJpaEntity}), reusable across many courses, and queryable on their own.
     * Contrast with {@code keywords} ({@code @ElementCollection}): tags have an {@code id} and
     * can be managed, renamed, or searched independently.
     */
    @ManyToMany
    @JoinTable(
            name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagJpaEntity> tags = new HashSet<>();
}
