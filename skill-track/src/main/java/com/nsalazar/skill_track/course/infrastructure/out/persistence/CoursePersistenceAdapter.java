package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.mapper.CoursePersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Persistence adapter that implements {@link CourseRepositoryPort} using Spring Data JPA.
 * Translates between the domain {@link Course} model and the JPA entity layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CoursePersistenceAdapter implements CourseRepositoryPort {

    private final CourseJpaRepository courseJpaRepository;
    private final CoursePersistenceMapper mapper;

    /**
     * Persists a course record to the database.
     *
     * @param course the domain course to save
     * @return the saved course with any generated fields populated
     */
    @Override
    public Course save(Course course) {
        log.debug("Saving course with title '{}'", course.title());
        return mapper.toDomain(courseJpaRepository.save(mapper.toJpaEntity(course)));
    }

    /**
     * Retrieves a course from the database by its id.
     *
     * @param id the course id
     * @return an {@link Optional} containing the course if found, or empty otherwise
     */
    @Override
    public Optional<Course> findById(UUID id) {
        log.debug("Finding course by id {}", id);
        return courseJpaRepository.findById(id).map(mapper::toDomain);
    }
}
