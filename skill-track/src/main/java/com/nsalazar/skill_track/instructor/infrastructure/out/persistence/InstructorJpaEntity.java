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

    /** Courses taught by this instructor. */
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity> courses
            = new ArrayList<>();
}
