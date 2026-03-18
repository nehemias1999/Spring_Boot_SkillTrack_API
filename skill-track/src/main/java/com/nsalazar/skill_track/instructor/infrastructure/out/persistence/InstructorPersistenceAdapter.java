package com.nsalazar.skill_track.instructor.infrastructure.out.persistence;

import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.mapper.InstructorPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Persistence adapter that implements {@link InstructorRepositoryPort} using Spring Data JPA.
 * Translates between the domain {@link Instructor} model and the JPA entity layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InstructorPersistenceAdapter implements InstructorRepositoryPort {

    private final InstructorJpaRepository instructorJpaRepository;
    private final InstructorPersistenceMapper mapper;

    /**
     * Persists an instructor record to the database.
     *
     * @param instructor the domain instructor to save
     * @return the saved instructor with any generated fields populated
     */
    @Override
    public Instructor save(Instructor instructor) {
        log.debug("Saving instructor with email '{}'", instructor.email());
        return mapper.toDomain(instructorJpaRepository.save(mapper.toJpaEntity(instructor)));
    }

    /**
     * Retrieves an instructor from the database by its id.
     *
     * @param id the instructor id
     * @return an {@link Optional} containing the instructor if found, or empty otherwise
     */
    @Override
    public Optional<Instructor> findById(UUID id) {
        log.debug("Finding instructor by id {}", id);
        return instructorJpaRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * Checks whether an instructor record exists for the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if an instructor with this email exists, {@code false} otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking existence of instructor with email '{}'", email);
        return instructorJpaRepository.existsByEmail(email);
    }
}
