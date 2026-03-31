package com.nsalazar.skill_track.review.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.shared.persistence.Auditable;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * JPA entity that maps to the {@code reviews} database table.
 * Enforces a composite unique constraint on {@code student_id} and {@code course_id}
 * to prevent duplicate reviews.
 */
@Entity
@Table(name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewJpaEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentJpaEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
