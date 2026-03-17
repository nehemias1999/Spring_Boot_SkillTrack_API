package com.nsalazar.skill_track.profile.application.usecase;

import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase;
import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service that handles the create-profile use case.
 * Validates that the referenced student exists and does not yet have a profile
 * before persisting the new profile.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateProfileService implements CreateProfileUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final ProfileRepositoryPort profileRepositoryPort;

    /**
     * Creates a profile for the student identified by {@code command.studentId()}.
     *
     * @param command the command containing the student id and profile details
     * @return the persisted {@link Profile} with its assigned id
     * @throws ResourceNotFoundException   if no student exists with the given id
     * @throws BusinessValidationException if a profile already exists for the student
     */
    @Override
    public Profile createProfile(CreateProfileCommand command) {
        log.info("Creating profile for studentId {}", command.studentId());
        if (!studentRepositoryPort.findById(command.studentId()).isPresent()) {
            log.warn("Student not found with id {}", command.studentId());
            throw new ResourceNotFoundException("Student not found with id: " + command.studentId());
        }
        if (profileRepositoryPort.existsByStudentId(command.studentId())) {
            log.warn("Profile already exists for studentId {}", command.studentId());
            throw new BusinessValidationException(
                    "A profile already exists for student with id: " + command.studentId());
        }
        Profile profile = new Profile(null, command.studentId(), command.bio(),
                command.linkedInUrl(), command.phoneNumber());
        Profile saved = profileRepositoryPort.save(profile);
        log.info("Profile created successfully with id {} for studentId {}", saved.id(), command.studentId());
        return saved;
    }
}
