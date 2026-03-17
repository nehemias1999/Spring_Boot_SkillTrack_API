package com.nsalazar.skill_track.student.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper that converts between the {@link Student} domain record and
 * the {@link StudentJpaEntity} JPA entity.
 */
@Mapper(componentModel = "spring")
public interface StudentPersistenceMapper {

    /**
     * Converts a domain {@link Student} to a {@link StudentJpaEntity} for persistence.
     *
     * @param student the domain student
     * @return the corresponding JPA entity
     */
    StudentJpaEntity toJpaEntity(Student student);

    /**
     * Converts a {@link StudentJpaEntity} retrieved from the database to a domain {@link Student}.
     *
     * @param entity the JPA entity
     * @return the corresponding domain student
     */
    Student toDomain(StudentJpaEntity entity);
}
