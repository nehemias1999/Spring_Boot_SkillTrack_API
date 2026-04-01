package com.nsalazar.skill_track.student.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Student} domain record and
 * the {@link StudentJpaEntity} JPA entity.
 * All scalar fields (including {@code version}) map by name; auditing fields are set by JPA.
 */
@Mapper(componentModel = "spring")
public interface StudentPersistenceMapper {

    /**
     * Converts a domain {@link Student} to a {@link StudentJpaEntity} for persistence.
     * Auditing fields are set by JPA and must not be overwritten.
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StudentJpaEntity toJpaEntity(Student student);

    /**
     * Converts a {@link StudentJpaEntity} retrieved from the database to a domain {@link Student}.
     */
    Student toDomain(StudentJpaEntity entity);
}
