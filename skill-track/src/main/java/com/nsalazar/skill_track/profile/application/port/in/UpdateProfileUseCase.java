package com.nsalazar.skill_track.profile.application.port.in;

import com.nsalazar.skill_track.profile.domain.Profile;
import java.util.UUID;

/**
 * Inbound port for partially updating a profile.
 */
public interface UpdateProfileUseCase {
    /**
     * Command for updating a profile. All fields are optional (null = keep existing value).
     */
    record UpdateProfileCommand(
            UUID studentId,
            String bio,
            String linkedInUrl,
            String phoneNumber,
            String githubUrl,
            String portfolioUrl,
            String avatarUrl
    ) {}

    /**
     * Updates the profile for the given student with the provided non-null fields.
     *
     * @param command the update command
     * @return the updated profile
     */
    Profile updateProfile(UpdateProfileCommand command);
}
