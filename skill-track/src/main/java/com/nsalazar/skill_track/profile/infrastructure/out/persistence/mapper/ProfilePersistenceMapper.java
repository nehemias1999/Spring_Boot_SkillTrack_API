package com.nsalazar.skill_track.profile.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.infrastructure.out.persistence.ProfileJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Profile} domain record and
 * the {@link ProfileJpaEntity} JPA entity, handling the nested {@code student.id} mapping.
 */
@Mapper(componentModel = "spring")
public interface ProfilePersistenceMapper {

    /**
     * Converts a domain {@link Profile} to a {@link ProfileJpaEntity} for persistence.
     * Maps {@code studentId} to the nested {@code student.id} field.
     *
     * @param profile the domain profile
     * @return the corresponding JPA entity
     */
    @Mapping(target = "student.id", source = "studentId")
    ProfileJpaEntity toJpaEntity(Profile profile);

    /**
     * Converts a {@link ProfileJpaEntity} retrieved from the database to a domain {@link Profile}.
     * Maps the nested {@code student.id} back to the flat {@code studentId} field.
     *
     * @param entity the JPA entity
     * @return the corresponding domain profile
     */
    @Mapping(target = "studentId", source = "student.id")
    Profile toDomain(ProfileJpaEntity entity);
}
