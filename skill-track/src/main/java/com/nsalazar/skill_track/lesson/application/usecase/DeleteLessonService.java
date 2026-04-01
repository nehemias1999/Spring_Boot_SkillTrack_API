package com.nsalazar.skill_track.lesson.application.usecase;

import com.nsalazar.skill_track.lesson.application.port.in.DeleteLessonUseCase;
import com.nsalazar.skill_track.lesson.domain.port.out.LessonRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Application service that deletes a lesson by its id.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class DeleteLessonService implements DeleteLessonUseCase {

    private final LessonRepositoryPort lessonRepositoryPort;

    @Override
    public void deleteLesson(UUID lessonId) {
        log.info("Deleting lesson with id {}", lessonId);
        if (lessonRepositoryPort.findById(lessonId).isEmpty()) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }
        lessonRepositoryPort.deleteById(lessonId);
        log.info("Lesson {} deleted successfully", lessonId);
    }
}
