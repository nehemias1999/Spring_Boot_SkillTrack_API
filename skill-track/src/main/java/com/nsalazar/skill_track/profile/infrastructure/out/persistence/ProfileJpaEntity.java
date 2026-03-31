package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import com.nsalazar.skill_track.shared.persistence.Auditable;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * JPA entity that maps to the {@code profiles} database table.
 * Holds a one-to-one relationship with {@link StudentJpaEntity}.
 * Used exclusively within the persistence layer; not exposed to the domain.
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
}
