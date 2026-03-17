package com.nsalazar.skill_track.instructor.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Instructor} domain record and
 * the {@link InstructorJpaEntity} JPA entity. The {@code courses} collection is ignored
 * during domain-to-entity conversion as it is managed separately by JPA.
 */
@Mapper(componentModel = "spring")
public interface InstructorPersistenceMapper {

    /**
     * Converts a domain {@link Instructor} to an {@link InstructorJpaEntity} for persistence.
     * The {@code courses} field is intentionally ignored.
     *
     * @param instructor the domain instructor
     * @return the corresponding JPA entity
     */
    @Mapping(target = "courses", ignore = true)
    InstructorJpaEntity toJpaEntity(Instructor instructor);

    /**
     * Converts an {@link InstructorJpaEntity} retrieved from the database to a domain {@link Instructor}.
     *
     * @param entity the JPA entity
     * @return the corresponding domain instructor
     */
    Instructor toDomain(InstructorJpaEntity entity);
}
