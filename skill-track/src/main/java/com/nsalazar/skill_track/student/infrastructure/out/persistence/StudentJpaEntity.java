package com.nsalazar.skill_track.student.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * JPA entity that maps to the {@code students} database table.
 * Used exclusively within the persistence layer; not exposed to the domain.
 */
@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentJpaEntity {

    /** Auto-generated UUID primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** The student's first name. */
    @Column(nullable = false)
    private String firstName;

    /** The student's last name. */
    @Column(nullable = false)
    private String lastName;

    /** The student's unique email address. */
    @Column(nullable = false, unique = true)
    private String email;
}
