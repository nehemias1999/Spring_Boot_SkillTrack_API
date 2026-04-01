package com.nsalazar.skill_track.lesson.application.usecase;

import com.nsalazar.skill_track.lesson.application.port.in.ListLessonsByCourseUseCase;
import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.domain.port.out.LessonRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Application service that retrieves all lessons for a given course, ordered by position.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListLessonsByCourseService implements ListLessonsByCourseUseCase {

    private final LessonRepositoryPort lessonRepositoryPort;

    @Override
    public List<Lesson> listLessonsByCourse(UUID courseId) {
        log.info("Listing lessons for courseId {}", courseId);
        return lessonRepositoryPort.findByCourseIdOrderByPosition(courseId);
    }
}
