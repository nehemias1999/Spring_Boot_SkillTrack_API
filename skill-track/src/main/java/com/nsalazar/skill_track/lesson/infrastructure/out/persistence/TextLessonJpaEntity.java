package com.nsalazar.skill_track.lesson.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity for text-type lessons.
 *
 * <p>Stored in the {@code text_lessons} table, joined to the base {@code lessons} table
 * by the shared primary key ({@code @PrimaryKeyJoinColumn}).
 * The discriminator value {@code "TEXT"} in {@code lessons.lesson_type} identifies rows
 * belonging to this subclass.
 *
 * <p>Only the text-specific column ({@code content}) lives here; everything else
 * (title, position, course_id) is in the base {@code lessons} table.
 */
@Entity
@Table(name = "text_lessons")
@PrimaryKeyJoinColumn(name = "lesson_id")
@DiscriminatorValue("TEXT")
@Getter
@Setter
@NoArgsConstructor
public class TextLessonJpaEntity extends LessonJpaEntity {

    /** Markdown-formatted body text of the lesson. */
    @Column(columnDefinition = "TEXT")
    private String content;
}
