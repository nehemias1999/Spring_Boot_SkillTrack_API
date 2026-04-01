package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import com.nsalazar.skill_track.shared.persistence.Auditable;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA entity that maps to the {@code profiles} database table.
 * Holds a one-to-one relationship with {@link StudentJpaEntity}.
 * Used exclusively within the persistence layer; not exposed to the domain.
 *
 * <p>{@code skills} demonstrates {@code @ElementCollection}: a set of simple string values
 * stored in the {@code profile_skills} table with a FK back to the profile — no entity identity,
 * no id column on the skill itself.
 */
@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileJpaEntity extends Auditable {

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

    /** The student who owns this profile. */
    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private StudentJpaEntity student;

    /** Optional free-text biography. */
    private String bio;

    /** Optional LinkedIn profile URL. */
    private String linkedInUrl;

    /** Optional phone number. */
    private String phoneNumber;

    /** Optional GitHub profile URL. */
    private String githubUrl;

    /** Optional personal portfolio / website URL. */
    private String portfolioUrl;

    /** Optional URL pointing to the student's profile picture. */
    private String avatarUrl;

    /**
     * Free-form skill tags (e.g. "Java", "Spring Boot") stored in the {@code profile_skills}
     * collection table. Demonstrates {@code @ElementCollection}: no entity class, no id, just
     * scalar strings referenced by the profile's PK. Loaded eagerly by default for element collections.
     */
    @ElementCollection
    @CollectionTable(name = "profile_skills", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();
}
