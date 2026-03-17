package com.nsalazar.skill_track.course.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Course} domain record and
 * the {@link CourseJpaEntity} JPA entity, handling the nested {@code instructor.id} mapping.
 */
@Mapper(componentModel = "spring")
public interface CoursePersistenceMapper {

    /**
     * Converts a domain {@link Course} to a {@link CourseJpaEntity} for persistence.
     * Maps {@code instructorId} to the nested {@code instructor.id} field.
     *
     * @param course the domain course
     * @return the corresponding JPA entity
     */
    @Mapping(target = "instructor.id", source = "instructorId")
    CourseJpaEntity toJpaEntity(Course course);

    /**
     * Converts a {@link CourseJpaEntity} retrieved from the database to a domain {@link Course}.
     * Maps the nested {@code instructor.id} back to the flat {@code instructorId} field.
     *
     * @param entity the JPA entity
     * @return the corresponding domain course
     */
    @Mapping(target = "instructorId", source = "instructor.id")
    Course toDomain(CourseJpaEntity entity);
}
