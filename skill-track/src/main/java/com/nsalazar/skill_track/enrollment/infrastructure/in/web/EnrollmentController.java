package com.nsalazar.skill_track.enrollment.infrastructure.in.web;

import com.nsalazar.skill_track.enrollment.application.port.in.EnrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto.EnrollmentResponse;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.mapper.EnrollmentWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing enrollment endpoints under
 * {@code /api/v1/students/{studentId}/enrollments}.
 * Delegates all business logic to the enroll-student use case port.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/students/{studentId}/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollStudentUseCase enrollStudentUseCase;
    private final EnrollmentWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/students/{studentId}/enrollments/{courseId}} —
     * enrolls the given student in the given course.
     *
     * @param studentId the path variable identifying the student
     * @param courseId  the path variable identifying the course
     * @return the created enrollment as an {@link EnrollmentResponse}
     */
    @PostMapping("/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        log.info("POST /api/v1/students/{}/enrollments/{}", studentId, courseId);
        return mapper.toResponse(enrollStudentUseCase.enrollStudent(studentId, courseId));
    }
}
