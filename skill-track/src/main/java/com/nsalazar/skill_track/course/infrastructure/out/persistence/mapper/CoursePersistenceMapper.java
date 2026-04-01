package com.nsalazar.skill_track.course.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CoursePrerequisiteJpaEntity;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.TagJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * MapStruct mapper that converts between the {@link Course} domain record and
 * the {@link CourseJpaEntity} JPA entity.
 *
 * <p>Notable mappings:
 * <ul>
 *   <li>{@code instructorId} ↔ {@code instructor.id} — nested object projection</li>
 *   <li>{@code prerequisiteIds} ← {@code prerequisites} — flat UUIDs from the join-entity set
 *       (uses a {@code java} expression to call the default helper {@link #extractPrerequisiteIds})</li>
 *   <li>{@code tagNames} ← {@code tags} — flat strings from the tag entity set
 *       (uses a {@code java} expression to call the default helper {@link #extractTagNames})</li>
 *   <li>{@code prerequisites} and {@code tags} are ignored on {@code toJpaEntity} because
 *       they are managed by dedicated services ({@code ManageCoursePrerequisiteService},
 *       {@code ManageCourseTagService}) via their own repository calls.</li>
 * </ul>
 */
@Mapper(componentModel = "spring")
public interface CoursePersistenceMapper {

    /**
     * Converts a domain {@link Course} to a {@link CourseJpaEntity} for persistence.
     * Collections ({@code prerequisites}, {@code tags}) are ignored here because they are
     * managed separately; {@code keywords} maps by name.
     */
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "prerequisites", ignore = true)
    @Mapping(target = "tags", ignore = true)
    CourseJpaEntity toJpaEntity(Course course);

    /**
     * Converts a {@link CourseJpaEntity} from the database to a domain {@link Course}.
     * Derives {@code prerequisiteIds} and {@code tagNames} from the entity's collection fields
     * via helper expressions, so callers receive flat IDs/strings rather than entity references.
     */
    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "prerequisiteIds",
            expression = "java(extractPrerequisiteIds(entity.getPrerequisites()))")
    @Mapping(target = "tagNames",
            expression = "java(extractTagNames(entity.getTags()))")
    Course toDomain(CourseJpaEntity entity);

    /** Extracts the prerequisite course UUIDs from the join-entity set. */
    default Set<UUID> extractPrerequisiteIds(Set<CoursePrerequisiteJpaEntity> prerequisites) {
        if (prerequisites == null || prerequisites.isEmpty()) return Collections.emptySet();
        return prerequisites.stream()
                .map(p -> p.getId().getPrerequisiteId())
                .collect(Collectors.toSet());
    }

    /** Extracts the tag name strings from the tag entity set. */
    default Set<String> extractTagNames(Set<TagJpaEntity> tags) {
        if (tags == null || tags.isEmpty()) return Collections.emptySet();
        return tags.stream()
                .map(TagJpaEntity::getName)
                .collect(Collectors.toSet());
    }
}
