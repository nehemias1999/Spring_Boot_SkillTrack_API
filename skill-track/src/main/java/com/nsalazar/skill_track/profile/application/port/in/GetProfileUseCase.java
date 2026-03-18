package com.nsalazar.skill_track.profile.application.port.in;

import com.nsalazar.skill_track.profile.domain.Profile;
import java.util.UUID;

/**
 * Inbound port for retrieving a student's profile.
 */
public interface GetProfileUseCase {
    /**
     * Returns the profile associated with the given student id.
     *
     * @param studentId the student id
     * @return the profile
     */
    Profile getProfileByStudentId(UUID studentId);
}
