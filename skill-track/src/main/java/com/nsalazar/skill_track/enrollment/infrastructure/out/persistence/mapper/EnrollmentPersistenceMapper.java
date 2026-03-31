package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.infrastructure.out.persistence.EnrollmentJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Enrollment} domain record and
 * the {@link EnrollmentJpaEntity} JPA entity, handling nested {@code student.id}
 * and {@code course.id} mappings.
 */
@Mapper(componentModel = "spring")
public interface EnrollmentPersistenceMapper {

    /**
     * Converts a domain {@link Enrollment} to an {@link EnrollmentJpaEntity} for persistence.
     * Maps {@code studentId} and {@code courseId} to the nested entity id fields.
     *
     * @param enrollment the domain enrollment
     * @return the corresponding JPA entity
     */
    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EnrollmentJpaEntity toJpaEntity(Enrollment enrollment);

    /**
     * Converts an {@link EnrollmentJpaEntity} retrieved from the database to a domain {@link Enrollment}.
     * Maps the nested {@code student.id} and {@code course.id} back to flat id fields.
     *
     * @param entity the JPA entity
     * @return the corresponding domain enrollment
     */
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    Enrollment toDomain(EnrollmentJpaEntity entity);
}
