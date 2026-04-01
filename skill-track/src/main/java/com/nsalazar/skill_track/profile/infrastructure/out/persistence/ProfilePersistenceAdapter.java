package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.profile.infrastructure.out.persistence.mapper.ProfilePersistenceMapper;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
    private final EntityManager entityManager;

    /**
     * Persists a profile record to the database.
     *
     * @param profile the domain profile to save
     * @return the saved profile with any generated fields populated
     */
    @Override
    public Profile save(Profile profile) {
        log.debug("Saving profile for studentId {}", profile.studentId());
        ProfileJpaEntity entity = mapper.toJpaEntity(profile);
        entity.setStudent(entityManager.getReference(StudentJpaEntity.class, profile.studentId()));
        return mapper.toDomain(profileJpaRepository.save(entity));
    }

    /**
     * Retrieves a profile from the database by the owning student's id.
     *
     * @param studentId the id of the student whose profile is requested
     * @return an {@link Optional} containing the profile if found, or empty otherwise
     */
    @Override
    public Optional<Profile> findByStudentId(UUID studentId) {
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
    public boolean existsByStudentId(UUID studentId) {
        log.debug("Checking existence of profile for studentId {}", studentId);
        return profileJpaRepository.existsByStudentId(studentId);
    }

    /**
     * Deletes the profile associated with the given student id.
     *
     * @param studentId the student id
     */
    @Override
    public void deleteByStudentId(UUID studentId) {
        log.debug("Deleting profile for studentId: {}", studentId);
        profileJpaRepository.deleteByStudentId(studentId);
    }
}
