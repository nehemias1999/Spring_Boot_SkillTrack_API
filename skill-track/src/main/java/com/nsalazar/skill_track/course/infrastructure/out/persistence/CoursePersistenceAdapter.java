package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.mapper.CoursePersistenceMapper;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final EntityManager entityManager;

    /**
     * Persists a course record to the database.
     *
     * @param course the domain course to save
     * @return the saved course with any generated fields populated
     */
    @Override
    public Course save(Course course) {
        log.debug("Saving course with title '{}'", course.title());
        CourseJpaEntity entity = mapper.toJpaEntity(course);
        // Use a Hibernate proxy for the instructor FK to avoid the "detached entity with null
        // version" error that occurs in Hibernate 7 when a stub entity (id set, version null)
        // is used as a @ManyToOne reference.
        entity.setInstructor(entityManager.getReference(InstructorJpaEntity.class, course.instructorId()));
        return mapper.toDomain(courseJpaRepository.save(entity));
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

    /**
     * Retrieves a course by id with its instructor eagerly loaded via JOIN FETCH,
     * preventing the N+1 select problem when the instructor data is needed.
     *
     * @param id the course id
     * @return an {@link Optional} containing the fully loaded course if found
     */
    @Override
    public Optional<Course> findByIdWithInstructor(UUID id) {
        log.debug("Finding course by id {} with instructor (JOIN FETCH)", id);
        return courseJpaRepository.findByIdWithInstructor(id).map(mapper::toDomain);
    }

    /**
     * Returns a paginated list of all courses.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of courses
     */
    @Override
    public Page<Course> findAll(Pageable pageable) {
        log.debug("Finding all courses with pageable: {}", pageable);
        return courseJpaRepository.findAll(pageable).map(mapper::toDomain);
    }

    /**
     * Deletes a course record from the database by its id.
     *
     * @param id the course id
     */
    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting course with id: {}", id);
        courseJpaRepository.deleteById(id);
    }
}
