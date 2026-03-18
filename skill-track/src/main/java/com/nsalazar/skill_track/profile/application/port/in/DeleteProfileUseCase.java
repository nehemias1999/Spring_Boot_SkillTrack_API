package com.nsalazar.skill_track.profile.application.port.in;

import java.util.UUID;

/**
 * Inbound port for deleting a student's profile.
 */
public interface DeleteProfileUseCase {
    /**
     * Deletes the profile associated with the given student id.
     *
     * @param studentId the student id
     */
    void deleteProfile(UUID studentId);
}
