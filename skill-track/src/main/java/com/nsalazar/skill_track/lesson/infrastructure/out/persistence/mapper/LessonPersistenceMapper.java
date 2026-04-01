package com.nsalazar.skill_track.lesson.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.domain.LessonType;
import com.nsalazar.skill_track.lesson.infrastructure.out.persistence.LessonJpaEntity;
import com.nsalazar.skill_track.lesson.infrastructure.out.persistence.TextLessonJpaEntity;
import com.nsalazar.skill_track.lesson.infrastructure.out.persistence.VideoLessonJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Manual mapper between the {@link Lesson} domain record and the {@link LessonJpaEntity}
 * inheritance hierarchy ({@link VideoLessonJpaEntity}, {@link TextLessonJpaEntity}).
 *
 * <p>MapStruct is not used here because it cannot easily handle polymorphic output types
 * (the target JPA entity differs based on {@link LessonType}). A hand-written component
 * with explicit {@code instanceof} dispatch is clearer and safer.
 *
 * <p>This demonstrates that mappers need not always be MapStruct interfaces — hand-written
 * Spring components are the right choice when the mapping logic involves branching on types.
 */
@Component
public class LessonPersistenceMapper {

    /**
     * Converts a {@link Lesson} domain record to the appropriate JPA entity subclass.
     * The {@code CourseJpaEntity} reference is built from {@code lesson.courseId()} — the
     * adapter must ensure a managed entity or proxy is used to avoid a redundant SELECT.
     *
     * @param lesson the domain lesson
     * @return a {@link VideoLessonJpaEntity} or {@link TextLessonJpaEntity}
     */
    public LessonJpaEntity toJpaEntity(Lesson lesson) {
        CourseJpaEntity courseRef = new CourseJpaEntity();
        courseRef.setId(lesson.courseId());

        return switch (lesson.type()) {
            case VIDEO -> {
                VideoLessonJpaEntity entity = new VideoLessonJpaEntity();
                populateBase(entity, lesson, courseRef);
                entity.setVideoUrl(lesson.videoUrl());
                entity.setDurationSeconds(lesson.durationSeconds());
                yield entity;
            }
            case TEXT -> {
                TextLessonJpaEntity entity = new TextLessonJpaEntity();
                populateBase(entity, lesson, courseRef);
                entity.setContent(lesson.content());
                yield entity;
            }
        };
    }

    /**
     * Converts any {@link LessonJpaEntity} subclass back to a {@link Lesson} domain record.
     * Uses {@code instanceof} pattern matching (Java 16+) to extract subclass-specific fields.
     *
     * @param entity the JPA entity (may be {@link VideoLessonJpaEntity} or {@link TextLessonJpaEntity})
     * @return the corresponding domain record
     */
    public Lesson toDomain(LessonJpaEntity entity) {
        if (entity instanceof VideoLessonJpaEntity video) {
            return new Lesson(
                    video.getId(),
                    video.getCourse().getId(),
                    video.getTitle(),
                    video.getPosition(),
                    LessonType.VIDEO,
                    video.getVideoUrl(),
                    video.getDurationSeconds(),
                    null
            );
        } else if (entity instanceof TextLessonJpaEntity text) {
            return new Lesson(
                    text.getId(),
                    text.getCourse().getId(),
                    text.getTitle(),
                    text.getPosition(),
                    LessonType.TEXT,
                    null,
                    null,
                    text.getContent()
            );
        }
        throw new IllegalStateException("Unknown LessonJpaEntity subtype: " + entity.getClass().getName());
    }

    private void populateBase(LessonJpaEntity entity, Lesson lesson, CourseJpaEntity courseRef) {
        entity.setId(lesson.id());
        entity.setCourse(courseRef);
        entity.setTitle(lesson.title());
        entity.setPosition(lesson.position());
    }
}
