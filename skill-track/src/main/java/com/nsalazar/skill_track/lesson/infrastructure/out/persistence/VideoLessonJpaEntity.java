package com.nsalazar.skill_track.lesson.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity for video-type lessons.
 *
 * <p>Stored in the {@code video_lessons} table, joined to the base {@code lessons} table
 * by the shared primary key ({@code @PrimaryKeyJoinColumn}).
 * The discriminator value {@code "VIDEO"} in {@code lessons.lesson_type} tells Hibernate
 * to join this table when loading a VIDEO lesson.
 *
 * <p>Only video-specific columns ({@code video_url}, {@code duration_seconds}) live here;
 * common columns (title, position, course_id) are in the base table.
 */
@Entity
@Table(name = "video_lessons")
@PrimaryKeyJoinColumn(name = "lesson_id")
@DiscriminatorValue("VIDEO")
@Getter
@Setter
@NoArgsConstructor
public class VideoLessonJpaEntity extends LessonJpaEntity {

    /** Publicly accessible URL of the video stream or file. */
    @Column(name = "video_url")
    private String videoUrl;

    /** Total duration of the video in seconds. */
    @Column(name = "duration_seconds")
    private Integer durationSeconds;
}
