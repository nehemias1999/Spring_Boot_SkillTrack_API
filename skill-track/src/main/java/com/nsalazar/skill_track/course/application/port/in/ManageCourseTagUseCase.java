package com.nsalazar.skill_track.course.application.port.in;

import java.util.UUID;

/**
 * Inbound port for managing the many-to-many relationship between courses and tags.
 * Tags are independent entities (with their own id and name) that can be shared across
 * many courses — contrast with {@code keywords}, which are simple {@code @ElementCollection}
 * strings without entity identity.
 */
public interface ManageCourseTagUseCase {

    /**
     * Adds a tag with the given name to the specified course.
     * If no tag with that name exists, a new {@code TagJpaEntity} is created automatically.
     *
     * @param courseId the course to tag
     * @param tagName  the tag name to add
     */
    void addTag(UUID courseId, String tagName);

    /**
     * Removes the tag with the given name from the specified course.
     * The {@code TagJpaEntity} itself is not deleted — it remains available for other courses.
     *
     * @param courseId the course to untag
     * @param tagName  the tag name to remove
     */
    void removeTag(UUID courseId, String tagName);
}
