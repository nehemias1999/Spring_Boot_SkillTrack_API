package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.ManageCoursePrerequisiteUseCase;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaRepository;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CoursePrerequisiteId;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CoursePrerequisiteJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CoursePrerequisiteJpaRepository;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Application service that manages the self-referential course-prerequisite relationship.
 *
 * <p>Demonstrates {@code @EmbeddedId} composite PK usage: the join-table entity
 * ({@link CoursePrerequisiteJpaEntity}) is looked up and persisted directly via its
 * own repository, rather than cascading through the owning {@code CourseJpaEntity}.
 * This keeps the prerequisite operations atomic and avoids loading the full course graph.
 *
 * <p>Note: this service operates at the JPA layer directly (bypassing the domain port)
 * because the prerequisite relationship lives entirely in the infrastructure; the domain
 * model carries only the derived {@code Set<UUID> prerequisiteIds} for read purposes.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class ManageCoursePrerequisiteService implements ManageCoursePrerequisiteUseCase {

    private final CourseJpaRepository courseJpaRepository;
    private final CoursePrerequisiteJpaRepository prerequisiteJpaRepository;

    /**
     * {@inheritDoc}
     *
     * <p>Validates that both courses exist and that the prerequisite is not being added
     * to itself (a self-loop), then persists a new {@link CoursePrerequisiteJpaEntity}.
     */
    @Override
    public void addPrerequisite(UUID courseId, UUID prerequisiteId) {
        log.info("Adding prerequisite {} to course {}", prerequisiteId, courseId);

        if (courseId.equals(prerequisiteId)) {
            throw new BusinessValidationException("A course cannot be its own prerequisite.");
        }

        CourseJpaEntity course = findCourseOrThrow(courseId);
        CourseJpaEntity prerequisite = findCourseOrThrow(prerequisiteId);

        CoursePrerequisiteId compositeId = new CoursePrerequisiteId(courseId, prerequisiteId);
        if (prerequisiteJpaRepository.existsById(compositeId)) {
            throw new BusinessValidationException(
                    "Course " + prerequisiteId + " is already a prerequisite of course " + courseId);
        }

        prerequisiteJpaRepository.save(new CoursePrerequisiteJpaEntity(compositeId, course, prerequisite));
        log.info("Prerequisite {} added to course {} successfully", prerequisiteId, courseId);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Deletes the {@link CoursePrerequisiteJpaEntity} identified by the composite key.
     * Throws {@link ResourceNotFoundException} if the relationship does not exist.
     */
    @Override
    public void removePrerequisite(UUID courseId, UUID prerequisiteId) {
        log.info("Removing prerequisite {} from course {}", prerequisiteId, courseId);

        CoursePrerequisiteId compositeId = new CoursePrerequisiteId(courseId, prerequisiteId);
        if (!prerequisiteJpaRepository.existsById(compositeId)) {
            throw new ResourceNotFoundException(
                    "Prerequisite relationship not found: course " + courseId + " → " + prerequisiteId);
        }

        prerequisiteJpaRepository.deleteById(compositeId);
        log.info("Prerequisite {} removed from course {} successfully", prerequisiteId, courseId);
    }

    private CourseJpaEntity findCourseOrThrow(UUID id) {
        return courseJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
}
