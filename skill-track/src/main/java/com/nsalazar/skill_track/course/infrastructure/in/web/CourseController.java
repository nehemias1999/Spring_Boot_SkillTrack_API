package com.nsalazar.skill_track.course.infrastructure.in.web;

import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.DeleteCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.GetCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.ListCoursesUseCase;
import com.nsalazar.skill_track.course.application.port.in.ManageCoursePrerequisiteUseCase;
import com.nsalazar.skill_track.course.application.port.in.ManageCourseTagUseCase;
import com.nsalazar.skill_track.course.application.port.in.UpdateCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.UpdateCourseUseCase.UpdateCourseCommand;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CreateCourseRequest;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CourseResponse;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.UpdateCourseRequest;
import com.nsalazar.skill_track.course.infrastructure.in.web.mapper.CourseWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing course endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final ListCoursesUseCase listCoursesUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final ManageCoursePrerequisiteUseCase manageCoursePrerequisiteUseCase;
    private final ManageCourseTagUseCase manageCourseTagUseCase;
    private final CourseWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/courses} — returns a paginated list of all courses.
     */
    @GetMapping("/courses")
    public Page<CourseResponse> listCourses(Pageable pageable) {
        log.info("GET /api/v1/courses - page: {}", pageable);
        return listCoursesUseCase.listCourses(pageable).map(mapper::toResponse);
    }

    /**
     * Handles {@code POST /api/v1/instructors/{instructorId}/courses} — creates a new course.
     */
    @PostMapping("/instructors/{instructorId}/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@PathVariable UUID instructorId, @Valid @RequestBody CreateCourseRequest request) {
        log.info("POST /api/v1/instructors/{}/courses - title: '{}'", instructorId, request.title());
        return mapper.toResponse(createCourseUseCase.createCourse(mapper.toCommand(instructorId, request)));
    }

    /**
     * Handles {@code GET /api/v1/courses/{id}} — retrieves a course by id.
     */
    @GetMapping("/courses/{id}")
    public CourseResponse getCourse(@PathVariable UUID id) {
        log.info("GET /api/v1/courses/{}", id);
        return mapper.toResponse(getCourseUseCase.getCourseById(id));
    }

    /**
     * Handles {@code PATCH /api/v1/courses/{id}} — partially updates a course.
     */
    @PatchMapping("/courses/{id}")
    public CourseResponse updateCourse(@PathVariable UUID id, @RequestBody UpdateCourseRequest request) {
        log.info("PATCH /api/v1/courses/{}", id);
        UpdateCourseCommand command = new UpdateCourseCommand(id, request.title(), request.description(),
                request.price(), request.category(), request.difficulty(), request.durationHours(),
                request.status(), request.keywords());
        return mapper.toResponse(updateCourseUseCase.updateCourse(command));
    }

    /**
     * Handles {@code DELETE /api/v1/courses/{id}} — deletes a course.
     */
    @DeleteMapping("/courses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID id) {
        log.info("DELETE /api/v1/courses/{}", id);
        deleteCourseUseCase.deleteCourse(id);
    }

    // ── Prerequisite endpoints ──────────────────────────────────────────────

    /**
     * Handles {@code POST /api/v1/courses/{id}/prerequisites/{prereqId}} —
     * adds a prerequisite course to the given course.
     * Demonstrates the self-referential {@code @ManyToMany} with {@code @EmbeddedId} composite PK.
     */
    @PostMapping("/courses/{id}/prerequisites/{prereqId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPrerequisite(@PathVariable UUID id, @PathVariable UUID prereqId) {
        log.info("POST /api/v1/courses/{}/prerequisites/{}", id, prereqId);
        manageCoursePrerequisiteUseCase.addPrerequisite(id, prereqId);
    }

    /**
     * Handles {@code DELETE /api/v1/courses/{id}/prerequisites/{prereqId}} —
     * removes a prerequisite from the given course.
     */
    @DeleteMapping("/courses/{id}/prerequisites/{prereqId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePrerequisite(@PathVariable UUID id, @PathVariable UUID prereqId) {
        log.info("DELETE /api/v1/courses/{}/prerequisites/{}", id, prereqId);
        manageCoursePrerequisiteUseCase.removePrerequisite(id, prereqId);
    }

    // ── Tag endpoints ───────────────────────────────────────────────────────

    /**
     * Handles {@code POST /api/v1/courses/{id}/tags/{tagName}} —
     * adds a tag to the given course, creating the tag entity if it does not yet exist.
     * Demonstrates {@code @ManyToMany @JoinTable} with independent tag lifecycle.
     */
    @PostMapping("/courses/{id}/tags/{tagName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTag(@PathVariable UUID id, @PathVariable String tagName) {
        log.info("POST /api/v1/courses/{}/tags/{}", id, tagName);
        manageCourseTagUseCase.addTag(id, tagName);
    }

    /**
     * Handles {@code DELETE /api/v1/courses/{id}/tags/{tagName}} —
     * removes a tag from the given course (the tag entity itself is not deleted).
     */
    @DeleteMapping("/courses/{id}/tags/{tagName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTag(@PathVariable UUID id, @PathVariable String tagName) {
        log.info("DELETE /api/v1/courses/{}/tags/{}", id, tagName);
        manageCourseTagUseCase.removeTag(id, tagName);
    }
}
