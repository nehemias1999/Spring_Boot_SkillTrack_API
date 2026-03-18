package com.nsalazar.skill_track.student.infrastructure.in.web;

import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.DeleteStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.GetStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.ListStudentsUseCase;
import com.nsalazar.skill_track.student.application.port.in.UpdateStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.UpdateStudentUseCase.UpdateStudentCommand;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.CreateStudentRequest;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.StudentResponse;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.UpdateStudentRequest;
import com.nsalazar.skill_track.student.infrastructure.in.web.mapper.StudentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing student endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;
    private final GetStudentUseCase getStudentUseCase;
    private final ListStudentsUseCase listStudentsUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;
    private final StudentWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/students} — returns a paginated list of all students.
     */
    @GetMapping
    public Page<StudentResponse> listStudents(Pageable pageable) {
        log.info("GET /api/v1/students - page: {}", pageable);
        return listStudentsUseCase.listStudents(pageable).map(mapper::toResponse);
    }

    /**
     * Handles {@code POST /api/v1/students} — creates a new student.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest request) {
        log.info("POST /api/v1/students - email: '{}'", request.email());
        return mapper.toResponse(createStudentUseCase.createStudent(mapper.toCommand(request)));
    }

    /**
     * Handles {@code GET /api/v1/students/{id}} — retrieves a student by id.
     */
    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable UUID id) {
        log.info("GET /api/v1/students/{}", id);
        return mapper.toResponse(getStudentUseCase.getStudentById(id));
    }

    /**
     * Handles {@code PATCH /api/v1/students/{id}} — partially updates a student.
     */
    @PatchMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable UUID id, @RequestBody UpdateStudentRequest request) {
        log.info("PATCH /api/v1/students/{}", id);
        UpdateStudentCommand command = new UpdateStudentCommand(id, request.firstName(), request.lastName(), request.email());
        return mapper.toResponse(updateStudentUseCase.updateStudent(command));
    }

    /**
     * Handles {@code DELETE /api/v1/students/{id}} — deletes a student.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable UUID id) {
        log.info("DELETE /api/v1/students/{}", id);
        deleteStudentUseCase.deleteStudent(id);
    }
}
