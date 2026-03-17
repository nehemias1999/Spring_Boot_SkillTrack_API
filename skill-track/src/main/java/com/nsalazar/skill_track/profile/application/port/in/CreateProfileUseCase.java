package com.nsalazar.skill_track.profile.application.port.in;

import com.nsalazar.skill_track.profile.domain.Profile;

/**
 * Input port for the create-profile use case.
 * Defines the command record and the single method that drives profile creation.
 */
public interface CreateProfileUseCase {

    /**
     * Immutable command object carrying the data required to create a student profile.
     *
     * @param studentId   the id of the student for whom the profile is being created
     * @param bio         optional free-text biography
     * @param linkedInUrl optional LinkedIn profile URL
     * @param phoneNumber optional phone number
     */
    record CreateProfileCommand(Long studentId, String bio, String linkedInUrl, String phoneNumber) {}

    /**
     * Creates a new profile for the student identified in the command.
     *
     * @param command the command containing the student id and profile details
     * @return the newly created {@link Profile} with its assigned id
     */
    Profile createProfile(CreateProfileCommand command);
}
