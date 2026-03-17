package com.nsalazar.skill_track.course.infrastructure.in.web;

import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.GetCourseUseCase;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CreateCourseRequest;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CourseResponse;
import com.nsalazar.skill_track.course.infrastructure.in.web.mapper.CourseWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing course endpoints.
 * Course creation is nested under instructors ({@code /api/v1/instructors/{instructorId}/courses})
 * while retrieval uses a top-level path ({@code /api/v1/courses/{id}}).
 * Delegates all business logic to the appropriate use-case ports.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final CourseWebMapper mapper;

    /**
     * Handles {@code POST /api/v1/instructors/{instructorId}/courses} — creates a new course.
     *
     * @param instructorId the path variable identifying the owning instructor
     * @param request      the validated request body containing course details
     * @return the created course as a {@link CourseResponse}
     */
    @PostMapping("/api/v1/instructors/{instructorId}/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(
            @PathVariable Long instructorId,
            @Valid @RequestBody CreateCourseRequest request) {
        log.info("POST /api/v1/instructors/{}/courses - title: '{}'", instructorId, request.title());
        return mapper.toResponse(createCourseUseCase.createCourse(mapper.toCommand(instructorId, request)));
    }

    /**
     * Handles {@code GET /api/v1/courses/{id}} — retrieves a course by id.
     *
     * @param id the path variable identifying the course
     * @return the matching course as a {@link CourseResponse}
     */
    @GetMapping("/api/v1/courses/{id}")
    public CourseResponse getCourse(@PathVariable Long id) {
        log.info("GET /api/v1/courses/{}", id);
        return mapper.toResponse(getCourseUseCase.getCourseById(id));
    }
}
