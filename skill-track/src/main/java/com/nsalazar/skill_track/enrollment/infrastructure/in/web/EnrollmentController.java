package com.nsalazar.skill_track.enrollment.infrastructure.in.web;

import com.nsalazar.skill_track.enrollment.application.port.in.EnrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.ListEnrollmentsByStudentUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.UnenrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.UpdateEnrollmentProgressUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.UpdateEnrollmentProgressUseCase.UpdateEnrollmentProgressCommand;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto.EnrollmentResponse;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto.UpdateProgressRequest;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.mapper.EnrollmentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing enrollment endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students/{studentId}/enrollments")
public class EnrollmentController {

    private final EnrollStudentUseCase enrollStudentUseCase;
    private final ListEnrollmentsByStudentUseCase listEnrollmentsByStudentUseCase;
    private final UnenrollStudentUseCase unenrollStudentUseCase;
    private final UpdateEnrollmentProgressUseCase updateEnrollmentProgressUseCase;
    private final EnrollmentWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/students/{studentId}/enrollments} — lists all enrollments for a student.
     */
    @GetMapping
    public List<EnrollmentResponse> listEnrollments(@PathVariable UUID studentId) {
        log.info("GET /api/v1/students/{}/enrollments", studentId);
        return listEnrollmentsByStudentUseCase.listEnrollmentsByStudent(studentId)
                .stream().map(mapper::toResponse).toList();
    }

    /**
     * Handles {@code POST /api/v1/students/{studentId}/enrollments/{courseId}} — enrolls a student in a course.
     */
    @PostMapping("/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        log.info("POST /api/v1/students/{}/enrollments/{}", studentId, courseId);
        return mapper.toResponse(enrollStudentUseCase.enrollStudent(studentId, courseId));
    }

    /**
     * Handles {@code PATCH /api/v1/students/{studentId}/enrollments/{courseId}/progress} — updates progress.
     */
    @PatchMapping("/{courseId}/progress")
    public EnrollmentResponse updateProgress(
            @PathVariable UUID studentId,
            @PathVariable UUID courseId,
            @Valid @RequestBody UpdateProgressRequest request) {
        log.info("PATCH /api/v1/students/{}/enrollments/{}/progress - progress: {}%",
                studentId, courseId, request.progressPercentage());
        UpdateEnrollmentProgressCommand command =
                new UpdateEnrollmentProgressCommand(studentId, courseId, request.progressPercentage());
        return mapper.toResponse(updateEnrollmentProgressUseCase.updateProgress(command));
    }

    /**
     * Handles {@code DELETE /api/v1/students/{studentId}/enrollments/{courseId}} — unenrolls a student from a course.
     */
    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unenroll(@PathVariable UUID studentId, @PathVariable UUID courseId) {
        log.info("DELETE /api/v1/students/{}/enrollments/{}", studentId, courseId);
        unenrollStudentUseCase.unenrollStudent(studentId, courseId);
    }
}
