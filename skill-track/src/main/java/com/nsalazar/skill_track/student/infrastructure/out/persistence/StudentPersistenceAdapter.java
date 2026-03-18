package com.nsalazar.skill_track.student.infrastructure.out.persistence;

import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.mapper.StudentPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Persistence adapter that implements {@link StudentRepositoryPort} using Spring Data JPA.
 * Translates between the domain {@link Student} model and the JPA entity layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudentPersistenceAdapter implements StudentRepositoryPort {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentPersistenceMapper mapper;

    /**
     * Persists a student record to the database.
     *
     * @param student the domain student to save
     * @return the saved student with any generated fields populated
     */
    @Override
    public Student save(Student student) {
        log.debug("Saving student with email '{}'", student.email());
        return mapper.toDomain(studentJpaRepository.save(mapper.toJpaEntity(student)));
    }

    /**
     * Retrieves a student from the database by its id.
     *
     * @param id the student id
     * @return an {@link Optional} containing the student if found, or empty otherwise
     */
    @Override
    public Optional<Student> findById(UUID id) {
        log.debug("Finding student by id {}", id);
        return studentJpaRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * Checks whether a student record exists for the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if a student with this email exists, {@code false} otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking existence of student with email '{}'", email);
        return studentJpaRepository.existsByEmail(email);
    }

    /**
     * Returns a paginated list of all students.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of students
     */
    @Override
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Finding all students with pageable: {}", pageable);
        return studentJpaRepository.findAll(pageable).map(mapper::toDomain);
    }

    /**
     * Deletes a student record from the database by its id.
     *
     * @param id the student id
     */
    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting student with id: {}", id);
        studentJpaRepository.deleteById(id);
    }
}
