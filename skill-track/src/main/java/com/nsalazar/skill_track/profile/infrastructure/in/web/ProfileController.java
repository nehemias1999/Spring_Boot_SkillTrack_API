package com.nsalazar.skill_track.profile.infrastructure.in.web;

import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase;
import com.nsalazar.skill_track.profile.application.port.in.DeleteProfileUseCase;
import com.nsalazar.skill_track.profile.application.port.in.GetProfileUseCase;
import com.nsalazar.skill_track.profile.application.port.in.UpdateProfileUseCase;
import com.nsalazar.skill_track.profile.application.port.in.UpdateProfileUseCase.UpdateProfileCommand;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.CreateProfileRequest;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.ProfileResponse;
import com.nsalazar.skill_track.profile.infrastructure.in.web.dto.UpdateProfileRequest;
import com.nsalazar.skill_track.profile.infrastructure.in.web.mapper.ProfileWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing profile endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students/{studentId}/profile")
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;
    private final ProfileWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/students/{studentId}/profile}.
     */
    @GetMapping
    public ProfileResponse getProfile(@PathVariable UUID studentId) {
        log.info("GET /api/v1/students/{}/profile", studentId);
        return mapper.toResponse(getProfileUseCase.getProfileByStudentId(studentId));
    }

    /**
     * Handles {@code POST /api/v1/students/{studentId}/profile} — creates a profile for a student.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(@PathVariable UUID studentId, @Valid @RequestBody CreateProfileRequest request) {
        log.info("POST /api/v1/students/{}/profile", studentId);
        return mapper.toResponse(createProfileUseCase.createProfile(mapper.toCommand(studentId, request)));
    }

    /**
     * Handles {@code PATCH /api/v1/students/{studentId}/profile} — partially updates a profile.
     */
    @PatchMapping
    public ProfileResponse updateProfile(@PathVariable UUID studentId, @RequestBody UpdateProfileRequest request) {
        log.info("PATCH /api/v1/students/{}/profile", studentId);
        UpdateProfileCommand command = new UpdateProfileCommand(studentId, request.bio(), request.linkedInUrl(), request.phoneNumber());
        return mapper.toResponse(updateProfileUseCase.updateProfile(command));
    }

    /**
     * Handles {@code DELETE /api/v1/students/{studentId}/profile} — deletes a profile.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable UUID studentId) {
        log.info("DELETE /api/v1/students/{}/profile", studentId);
        deleteProfileUseCase.deleteProfile(studentId);
    }
}
