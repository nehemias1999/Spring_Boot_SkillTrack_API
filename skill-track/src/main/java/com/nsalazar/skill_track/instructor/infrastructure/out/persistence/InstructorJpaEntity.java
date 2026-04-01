package com.nsalazar.skill_track.instructor.infrastructure.out.persistence;

import com.nsalazar.skill_track.shared.persistence.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JPA entity that maps to the {@code instructors} database table.
 * Owns the one-to-many relationship with course entities.
 * Used exclusively within the persistence layer; not exposed to the domain.
 *
 * <p>{@code address} demonstrates {@code @Embedded}: the three address columns
 * ({@code street}, {@code city}, {@code country}) are stored directly in this table
 * (no join, no extra table) via the {@link AddressEmbeddable} value object.
 */
@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorJpaEntity extends Auditable {

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

    /** The instructor's first name. */
    @Column(nullable = false)
    private String firstName;

    /** The instructor's last name. */
    @Column(nullable = false)
    private String lastName;

    /** The instructor's unique email address. */
    @Column(nullable = false, unique = true)
    private String email;

    /** Optional free-text biography. */
    private String bio;

    /**
     * Optional physical address stored as three inline columns in the {@code instructors} table.
     * Demonstrates {@code @Embedded}: the {@link AddressEmbeddable} value object is not a
     * separate entity — it has no id, no table of its own, and no independent lifecycle.
     * All its fields are mapped as columns directly in this entity's table.
     */
    @Embedded
    private AddressEmbeddable address;

    /** Courses taught by this instructor. */
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity> courses
            = new ArrayList<>();
}
