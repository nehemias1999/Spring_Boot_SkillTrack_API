package com.nsalazar.skill_track.student.infrastructure.in.web;

import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.GetStudentUseCase;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.CreateStudentRequest;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.StudentResponse;
import com.nsalazar.skill_track.student.infrastructure.in.web.mapper.StudentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing student endpoints under {@code /api/v1/students}.
 * Delegates all business logic to the appropriate use-case ports.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;
    private final GetStudentUseCase getStudentUseCase;
    private final StudentWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/students} — creates a new student.
     *
     * @param request the validated request body containing student details
     * @return the created student as a {@link StudentResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest request) {
        log.info("POST /api/v1/students - email: '{}'", request.email());
        return mapper.toResponse(createStudentUseCase.createStudent(mapper.toCommand(request)));
    }

    /**
     * Handles {@code GET /api/v1/students/{id}} — retrieves a student by id.
     *
     * @param id the path variable identifying the student
     * @return the matching student as a {@link StudentResponse}
     */
    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        log.info("GET /api/v1/students/{}", id);
        return mapper.toResponse(getStudentUseCase.getStudentById(id));
    }
}
