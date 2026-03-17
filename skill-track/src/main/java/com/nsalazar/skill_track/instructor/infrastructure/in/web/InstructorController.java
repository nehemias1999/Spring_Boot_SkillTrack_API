package com.nsalazar.skill_track.instructor.infrastructure.in.web;

import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.GetInstructorUseCase;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.CreateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.InstructorResponse;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.mapper.InstructorWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing instructor endpoints under {@code /api/v1/instructors}.
 * Delegates all business logic to the appropriate use-case ports.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final CreateInstructorUseCase createInstructorUseCase;
    private final GetInstructorUseCase getInstructorUseCase;
    private final InstructorWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/instructors} — creates a new instructor.
     *
     * @param request the validated request body containing instructor details
     * @return the created instructor as an {@link InstructorResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorResponse createInstructor(@Valid @RequestBody CreateInstructorRequest request) {
        log.info("POST /api/v1/instructors - email: '{}'", request.email());
        return mapper.toResponse(createInstructorUseCase.createInstructor(mapper.toCommand(request)));
    }

    /**
     * Handles {@code GET /api/v1/instructors/{id}} — retrieves an instructor by id.
     *
     * @param id the path variable identifying the instructor
     * @return the matching instructor as an {@link InstructorResponse}
     */
    @GetMapping("/{id}")
    public InstructorResponse getInstructor(@PathVariable Long id) {
        log.info("GET /api/v1/instructors/{}", id);
        return mapper.toResponse(getInstructorUseCase.getInstructorById(id));
    }
}
