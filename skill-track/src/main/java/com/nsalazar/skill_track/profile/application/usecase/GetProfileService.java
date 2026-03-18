package com.nsalazar.skill_track.profile.application.usecase;

import com.nsalazar.skill_track.profile.application.port.in.GetProfileUseCase;
import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.domain.port.out.ProfileRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for retrieving a student's profile.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProfileService implements GetProfileUseCase {

    private final ProfileRepositoryPort profileRepositoryPort;

    /**
     * Returns the profile for the given student id.
     *
     * @param studentId the student id
     * @return the profile
     */
    @Override
    public Profile getProfileByStudentId(UUID studentId) {
        log.info("Fetching profile for studentId '{}'", studentId);
        return profileRepositoryPort.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for studentId: " + studentId));
    }
}
