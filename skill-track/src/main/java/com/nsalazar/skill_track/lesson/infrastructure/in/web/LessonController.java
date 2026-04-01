package com.nsalazar.skill_track.lesson.infrastructure.in.web;

import com.nsalazar.skill_track.lesson.application.port.in.CreateLessonUseCase;
import com.nsalazar.skill_track.lesson.application.port.in.DeleteLessonUseCase;
import com.nsalazar.skill_track.lesson.application.port.in.ListLessonsByCourseUseCase;
import com.nsalazar.skill_track.lesson.infrastructure.in.web.dto.CreateLessonRequest;
import com.nsalazar.skill_track.lesson.infrastructure.in.web.dto.LessonResponse;
import com.nsalazar.skill_track.lesson.infrastructure.in.web.mapper.LessonWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing lesson endpoints under {@code /api/v1/courses/{courseId}/lessons}.
 *
 * <p>Demonstrates the {@code @Inheritance(JOINED)} strategy in action: a single POST endpoint
 * accepts both VIDEO and TEXT lessons; the type discriminator in the request body determines
 * which concrete JPA entity subclass is persisted.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses/{courseId}/lessons")
public class LessonController {

    private final CreateLessonUseCase createLessonUseCase;
    private final ListLessonsByCourseUseCase listLessonsByCourseUseCase;
    private final DeleteLessonUseCase deleteLessonUseCase;
    private final LessonWebMapper mapper;

    /**
     * Handles {@code GET /api/v1/courses/{courseId}/lessons} —
     * returns all lessons for the course ordered by position.
     */
    @GetMapping
    public List<LessonResponse> listLessons(@PathVariable UUID courseId) {
        log.info("GET /api/v1/courses/{}/lessons", courseId);
        return listLessonsByCourseUseCase.listLessonsByCourse(courseId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Handles {@code POST /api/v1/courses/{courseId}/lessons} — creates a lesson.
     * Set {@code type} to {@code VIDEO} or {@code TEXT}; supply the matching type-specific fields.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonResponse createLesson(@PathVariable UUID courseId,
                                       @Valid @RequestBody CreateLessonRequest request) {
        log.info("POST /api/v1/courses/{}/lessons - type={}, title='{}'",
                courseId, request.type(), request.title());
        return mapper.toResponse(createLessonUseCase.createLesson(mapper.toCommand(courseId, request)));
    }

    /**
     * Handles {@code DELETE /api/v1/courses/{courseId}/lessons/{lessonId}} — deletes a lesson.
     */
    @DeleteMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable UUID courseId, @PathVariable UUID lessonId) {
        log.info("DELETE /api/v1/courses/{}/lessons/{}", courseId, lessonId);
        deleteLessonUseCase.deleteLesson(lessonId);
    }
}
