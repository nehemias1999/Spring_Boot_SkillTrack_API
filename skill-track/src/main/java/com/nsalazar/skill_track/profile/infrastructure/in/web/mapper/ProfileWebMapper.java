package com.nsalazar.skill_track.profile.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase.CreateProfileCommand;
import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.CreateProfileRequest;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

/**
 * MapStruct mapper that converts between the web layer DTOs and the application-layer
 * command/domain types for the profile bounded context.
 */
@Mapper(componentModel = "spring")
public interface ProfileWebMapper {

    /**
     * Builds a {@link CreateProfileCommand} by combining the path-variable student id
     * with the fields from the request body.
     *
     * @param studentId the id extracted from the URL path
     * @param request   the request body DTO
     * @return the corresponding use-case command
     */
    @Mapping(target = "studentId", source = "studentId")
    CreateProfileCommand toCommand(UUID studentId, CreateProfileRequest request);

    /**
     * Converts a {@link Profile} domain object into a {@link ProfileResponse} DTO.
     *
     * @param profile the domain profile
     * @return the response DTO suitable for serialization
     */
    ProfileResponse toResponse(Profile profile);
}
