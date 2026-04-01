package com.nsalazar.skill_track.lesson.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaRepository;
import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.domain.port.out.LessonRepositoryPort;
import com.nsalazar.skill_track.lesson.infrastructure.out.persistence.mapper.LessonPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Persistence adapter that implements {@link LessonRepositoryPort} using Spring Data JPA.
 *
 * <p>The lesson hierarchy uses {@code @Inheritance(JOINED)}: the JPA repository returns
 * polymorphic results (a mix of {@link VideoLessonJpaEntity} and {@link TextLessonJpaEntity}),
 * and the {@link LessonPersistenceMapper} dispatches on the concrete type to build
 * the appropriate {@link Lesson} domain record.
 *
 * <p>On save, the adapter resolves the {@link com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity}
 * reference via {@code getReferenceById} (returns a Hibernate proxy, avoiding a full SELECT)
 * so that only the FK column is written — not the entire course graph.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements LessonRepositoryPort {

    private final LessonJpaRepository lessonJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final LessonPersistenceMapper mapper;

    @Override
    public Lesson save(Lesson lesson) {
        log.debug("Saving {} lesson '{}'", lesson.type(), lesson.title());
        LessonJpaEntity entity = mapper.toJpaEntity(lesson);
        // Replace stub with a proper JPA proxy so Hibernate writes only the FK
        entity.setCourse(courseJpaRepository.getReferenceById(lesson.courseId()));
        return mapper.toDomain(lessonJpaRepository.save(entity));
    }

    @Override
    public Optional<Lesson> findById(UUID id) {
        log.debug("Finding lesson by id {}", id);
        return lessonJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Lesson> findByCourseIdOrderByPosition(UUID courseId) {
        log.debug("Listing lessons for courseId {}", courseId);
        return lessonJpaRepository.findByCourseIdOrderByPositionAsc(courseId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting lesson with id {}", id);
        lessonJpaRepository.deleteById(id);
    }
}
