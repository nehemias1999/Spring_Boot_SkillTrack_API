package com.nsalazar.skill_track.profile.infrastructure.in.web;

import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.CreateProfileRequest;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.ProfileResponse;
import com.nsalazar.skill_track.profile.infrastructure.in.web.mapper.ProfileWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing profile endpoints under {@code /api/v1/students/{studentId}/profile}.
 * Delegates all business logic to the appropriate use-case ports.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/students/{studentId}/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final ProfileWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/students/{studentId}/profile} — creates a profile for the given student.
     *
     * @param studentId the path variable identifying the student
     * @param request   the request body containing profile details
     * @return the created profile as a {@link ProfileResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(
            @PathVariable UUID studentId,
            @RequestBody CreateProfileRequest request) {
        log.info("POST /api/v1/students/{}/profile", studentId);
        return mapper.toResponse(
                createProfileUseCase.createProfile(mapper.toCommand(studentId, request)));
    }
}
