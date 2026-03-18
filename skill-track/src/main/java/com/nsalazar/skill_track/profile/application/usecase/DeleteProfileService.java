package com.nsalazar.skill_track.profile.application.usecase;

import com.nsalazar.skill_track.profile.application.port.in.DeleteProfileUseCase;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for deleting a student's profile.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeleteProfileService implements DeleteProfileUseCase {

    private final ProfileRepositoryPort profileRepositoryPort;

    /**
     * Verifies the profile exists then deletes it.
     *
     * @param studentId the student id
     */
    @Override
    public void deleteProfile(UUID studentId) {
        log.info("Deleting profile for studentId '{}'", studentId);
        profileRepositoryPort.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for studentId: " + studentId));
        profileRepositoryPort.deleteByStudentId(studentId);
        log.info("Profile for studentId '{}' deleted successfully", studentId);
    }
}
