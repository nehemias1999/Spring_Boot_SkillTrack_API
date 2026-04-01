package com.nsalazar.skill_track.course.application.usecase;

import com.nsalazar.skill_track.course.application.port.in.ManageCourseTagUseCase;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaRepository;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.TagJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.TagJpaRepository;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Application service that manages the many-to-many relationship between courses and tags.
 *
 * <p>Demonstrates {@code @ManyToMany @JoinTable}: tags are independent entities reused across
 * courses. If a tag with the requested name does not yet exist, it is created on the fly (upsert).
 * The owning side of the relationship is {@code CourseJpaEntity#tags}; mutations are flushed
 * when the transaction commits.
 *
 * <p>Cache: modifying course tags evicts the {@code "courses"} Caffeine cache so that list
 * responses do not return stale tag data.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class ManageCourseTagService implements ManageCourseTagUseCase {

    private final CourseJpaRepository courseJpaRepository;
    private final TagJpaRepository tagJpaRepository;

    /**
     * {@inheritDoc}
     *
     * <p>Finds or creates the {@link TagJpaEntity} for {@code tagName}, then adds it to the
     * course's tag collection. The join-table row is inserted when the transaction commits.
     */
    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public void addTag(UUID courseId, String tagName) {
        log.info("Adding tag '{}' to course {}", tagName, courseId);

        CourseJpaEntity course = findCourseOrThrow(courseId);
        TagJpaEntity tag = tagJpaRepository.findByName(tagName)
                .orElseGet(() -> {
                    log.debug("Tag '{}' not found — creating a new one", tagName);
                    TagJpaEntity newTag = new TagJpaEntity();
                    newTag.setName(tagName);
                    return tagJpaRepository.save(newTag);
                });

        if (course.getTags().contains(tag)) {
            throw new BusinessValidationException(
                    "Tag '" + tagName + "' is already assigned to course " + courseId);
        }

        course.getTags().add(tag);
        log.info("Tag '{}' added to course {} successfully", tagName, courseId);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Looks up the tag by name, verifies it is currently attached to the course, then
     * removes it from the owning-side collection. The join-table row is deleted on commit.
     */
    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public void removeTag(UUID courseId, String tagName) {
        log.info("Removing tag '{}' from course {}", tagName, courseId);

        CourseJpaEntity course = findCourseOrThrow(courseId);
        TagJpaEntity tag = tagJpaRepository.findByName(tagName)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagName));

        if (!course.getTags().remove(tag)) {
            throw new BusinessValidationException(
                    "Tag '" + tagName + "' is not assigned to course " + courseId);
        }

        log.info("Tag '{}' removed from course {} successfully", tagName, courseId);
    }

    private CourseJpaEntity findCourseOrThrow(UUID id) {
        return courseJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
}
