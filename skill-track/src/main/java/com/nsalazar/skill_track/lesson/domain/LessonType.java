package com.nsalazar.skill_track.lesson.domain;

/**
 * Discriminates the concrete type of a {@link Lesson}.
 * Maps to the {@code @DiscriminatorColumn} value in the JOINED inheritance table strategy.
 */
public enum LessonType {
    VIDEO,
    TEXT
}
