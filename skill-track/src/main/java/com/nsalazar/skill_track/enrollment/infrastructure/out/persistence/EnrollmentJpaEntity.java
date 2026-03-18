package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA entity that maps to the {@code enrollments} database table.
 * Enforces a composite unique constraint on {@code student_id} and {@code course_id}
 * to prevent duplicate enrollments at the database level.
 * Used exclusively within the persistence layer; not exposed to the domain.
 */
@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentJpaEntity {

    /** Auto-generated UUID primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** The enrolled student. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentJpaEntity student;

    /** The course the student enrolled in. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;

    /** The timestamp at which the enrollment was created. */
    @Column(nullable = false)
    private LocalDateTime enrolledAt;
}
