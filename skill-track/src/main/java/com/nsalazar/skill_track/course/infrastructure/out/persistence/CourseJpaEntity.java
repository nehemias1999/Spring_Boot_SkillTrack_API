package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * JPA entity that maps to the {@code courses} database table.
 * Holds a many-to-one relationship with {@link InstructorJpaEntity}.
 * Used exclusively within the persistence layer; not exposed to the domain.
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseJpaEntity {

    /** Auto-generated primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
