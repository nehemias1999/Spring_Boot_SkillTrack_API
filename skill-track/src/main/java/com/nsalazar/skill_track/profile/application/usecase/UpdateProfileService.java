package com.nsalazar.skill_track.profile.application.usecase;

import com.nsalazar.skill_track.profile.application.port.in.UpdateProfileUseCase;
import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Application service for partially updating a student's profile.
 */
@Slf4j
@Service
@Validated
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
        if (command.bio() == null && command.linkedInUrl() == null && command.phoneNumber() == null
                && command.githubUrl() == null && command.portfolioUrl() == null && command.avatarUrl() == null
                && command.skills() == null) {
            throw new BusinessValidationException("At least one field must be provided for update");
        }
        Profile existing = profileRepositoryPort.findByStudentId(command.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for studentId: " + command.studentId()));

        Profile updated = new Profile(
                existing.id(),
                existing.studentId(),
                command.bio() != null ? command.bio() : existing.bio(),
                command.linkedInUrl() != null ? command.linkedInUrl() : existing.linkedInUrl(),
                command.phoneNumber() != null ? command.phoneNumber() : existing.phoneNumber(),
                command.githubUrl() != null ? command.githubUrl() : existing.githubUrl(),
                command.portfolioUrl() != null ? command.portfolioUrl() : existing.portfolioUrl(),
                command.avatarUrl() != null ? command.avatarUrl() : existing.avatarUrl(),
                command.skills() != null ? command.skills() : existing.skills(),
                existing.version()
        );
        Profile saved = profileRepositoryPort.save(updated);
        log.info("Profile for studentId '{}' updated successfully", saved.studentId());
        return saved;
    }
}
