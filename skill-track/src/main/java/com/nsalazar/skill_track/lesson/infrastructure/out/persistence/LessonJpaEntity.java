package com.nsalazar.skill_track.lesson.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.lesson.domain.LessonType;
import com.nsalazar.skill_track.shared.persistence.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Abstract base JPA entity for all lesson types.
 *
 * <p>Uses {@link InheritanceType#JOINED}: the shared columns live in the {@code lessons} table,
 * while each concrete subclass ({@link VideoLessonJpaEntity}, {@link TextLessonJpaEntity}) has its
 * own child table containing only the type-specific columns. The child-table PK is a FK back to
 * {@code lessons.id}, joined by Hibernate on every load.
 *
 * <p>A {@code @DiscriminatorColumn} (default name {@code dtype}) stored in the base table tells
 * Hibernate which subclass table to join, making polymorphic queries efficient without loading
 * unused columns.
 *
 * <p>This is the most normalised inheritance strategy — no nulls in type-specific columns across
 * sibling rows — at the cost of an extra JOIN per query.
 */
@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "lesson_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class LessonJpaEntity extends Auditable {

    /** Auto-generated UUID primary key, shared by all subclass tables via PK-FK join. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** The course this lesson belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;

    /** Display title of the lesson. */
    @Column(nullable = false)
    private String title;

    /** Ordering position within the course (1-based). */
    @Column(nullable = false)
    private Integer position;

    /**
     * Lesson type stored as the discriminator value.
     * Marked {@code insertable = false, updatable = false} so Hibernate owns the column
     * via {@code @DiscriminatorColumn}; we read it for mapping purposes only.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", insertable = false, updatable = false)
    private LessonType lessonType;
}
