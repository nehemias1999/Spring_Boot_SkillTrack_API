package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.profile.infrastructure.out.persistence.mapper.ProfilePersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Persistence adapter that implements {@link ProfileRepositoryPort} using Spring Data JPA.
 * Translates between the domain {@link Profile} model and the JPA entity layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements ProfileRepositoryPort {

    private final ProfileJpaRepository profileJpaRepository;
    private final ProfilePersistenceMapper mapper;

    /**
     * Persists a profile record to the database.
     *
     * @param profile the domain profile to save
     * @return the saved profile with any generated fields populated
     */
    @Override
    public Profile save(Profile profile) {
        log.debug("Saving profile for studentId {}", profile.studentId());
        return mapper.toDomain(profileJpaRepository.save(mapper.toJpaEntity(profile)));
    }

    /**
     * Retrieves a profile from the database by the owning student's id.
     *
     * @param studentId the id of the student whose profile is requested
     * @return an {@link Optional} containing the profile if found, or empty otherwise
     */
    @Override
    public Optional<Profile> findByStudentId(Long studentId) {
        log.debug("Finding profile by studentId {}", studentId);
        return profileJpaRepository.findByStudentId(studentId).map(mapper::toDomain);
    }

    /**
     * Checks whether a profile record exists for the given student id.
     *
     * @param studentId the id of the student to check
     * @return {@code true} if a profile exists, {@code false} otherwise
     */
    @Override
    public boolean existsByStudentId(Long studentId) {
        log.debug("Checking existence of profile for studentId {}", studentId);
        return profileJpaRepository.existsByStudentId(studentId);
    }
}
