package com.nsalazar.skill_track.lesson.application.usecase;

import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.lesson.application.port.in.CreateLessonUseCase;
import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.domain.port.out.LessonRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Application service that handles the create-lesson use case.
 * Validates the parent course exists and applies type-specific field rules before persisting.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class CreateLessonService implements CreateLessonUseCase {

    private final CourseRepositoryPort courseRepositoryPort;
    private final LessonRepositoryPort lessonRepositoryPort;

    /**
     * {@inheritDoc}
     *
     * @throws ResourceNotFoundException   if the referenced course does not exist
     * @throws BusinessValidationException if type-specific required fields are missing
     */
    @Override
    public Lesson createLesson(CreateLessonCommand command) {
        log.info("Creating {} lesson '{}' for courseId {}", command.type(), command.title(), command.courseId());

        if (courseRepositoryPort.findById(command.courseId()).isEmpty()) {
            throw new ResourceNotFoundException("Course not found with id: " + command.courseId());
        }

        switch (command.type()) {
            case VIDEO -> {
                if (command.videoUrl() == null || command.videoUrl().isBlank()) {
                    throw new BusinessValidationException("videoUrl is required for VIDEO lessons.");
                }
            }
            case TEXT -> {
                if (command.content() == null || command.content().isBlank()) {
                    throw new BusinessValidationException("content is required for TEXT lessons.");
                }
            }
        }

        Lesson lesson = new Lesson(
                null,
                command.courseId(),
                command.title(),
                command.position(),
                command.type(),
                command.videoUrl(),
                command.durationSeconds(),
                command.content()
        );

        Lesson saved = lessonRepositoryPort.save(lesson);
        log.info("Lesson created with id {} (type={})", saved.id(), saved.type());
        return saved;
    }
}
