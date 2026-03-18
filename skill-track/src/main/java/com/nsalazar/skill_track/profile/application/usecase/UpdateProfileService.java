package com.nsalazar.skill_track.profile.application.usecase;

import com.nsalazar.skill_track.profile.application.port.in.UpdateProfileUseCase;
import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for partially updating a student's profile.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProfileService implements UpdateProfileUseCase {

    private final ProfileRepositoryPort profileRepositoryPort;

    /**
     * Fetches the existing profile and applies only the non-null fields from the command.
     *
     * @param command the update command
     * @return the updated profile
     */
    @Override
    public Profile updateProfile(UpdateProfileCommand command) {
        log.info("Updating profile for studentId '{}'", command.studentId());
        Profile existing = profileRepositoryPort.findByStudentId(command.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for studentId: " + command.studentId()));

        Profile updated = new Profile(
                existing.id(),
                existing.studentId(),
                command.bio() != null ? command.bio() : existing.bio(),
                command.linkedInUrl() != null ? command.linkedInUrl() : existing.linkedInUrl(),
                command.phoneNumber() != null ? command.phoneNumber() : existing.phoneNumber()
        );
        Profile saved = profileRepositoryPort.save(updated);
        log.info("Profile for studentId '{}' updated successfully", saved.studentId());
        return saved;
    }
}
