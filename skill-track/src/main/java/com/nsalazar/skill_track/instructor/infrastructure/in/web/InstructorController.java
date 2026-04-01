package com.nsalazar.skill_track.instructor.infrastructure.in.web;

import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.GetInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.ListInstructorsUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.UpdateInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.UpdateInstructorUseCase.UpdateInstructorCommand;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.CreateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.InstructorResponse;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.UpdateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.mapper.InstructorWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing instructor endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    private final CreateInstructorUseCase createInstructorUseCase;
    private final GetInstructorUseCase getInstructorUseCase;
    private final ListInstructorsUseCase listInstructorsUseCase;
    private final UpdateInstructorUseCase updateInstructorUseCase;
    private final InstructorWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/instructors} — returns a paginated list of all instructors.
     */
    @GetMapping
    public Page<InstructorResponse> listInstructors(Pageable pageable) {
        log.info("GET /api/v1/instructors - page: {}", pageable);
        return listInstructorsUseCase.listInstructors(pageable).map(mapper::toResponse);
    }

    /**
     * Handles {@code POST /api/v1/instructors} — creates a new instructor.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorResponse createInstructor(@Valid @RequestBody CreateInstructorRequest request) {
        log.info("POST /api/v1/instructors - email: '{}'", request.email());
        return mapper.toResponse(createInstructorUseCase.createInstructor(mapper.toCommand(request)));
    }

    /**
     * Handles {@code GET /api/v1/instructors/{id}} — retrieves an instructor by id.
     */
    @GetMapping("/{id}")
    public InstructorResponse getInstructor(@PathVariable UUID id) {
        log.info("GET /api/v1/instructors/{}", id);
        return mapper.toResponse(getInstructorUseCase.getInstructorById(id));
    }

    /**
     * Handles {@code PATCH /api/v1/instructors/{id}} — partially updates an instructor.
     */
    @PatchMapping("/{id}")
    public InstructorResponse updateInstructor(@PathVariable UUID id, @RequestBody UpdateInstructorRequest request) {
        log.info("PATCH /api/v1/instructors/{}", id);
        UpdateInstructorCommand command = new UpdateInstructorCommand(id, request.firstName(), request.lastName(),
                request.email(), request.bio(),
                request.address() != null
                        ? new com.nsalazar.skill_track.instructor.domain.Address(
                                request.address().street(), request.address().city(), request.address().country())
                        : null);
        return mapper.toResponse(updateInstructorUseCase.updateInstructor(command));
    }
}
