package com.nsalazar.skill_track.profile.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.infrastructure.out.persistence.ProfileJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Profile} domain record and
 * the {@link ProfileJpaEntity} JPA entity.
 *
 * <p>{@code skills} (a {@code Set<String>} on both sides) and {@code version} map by name.
 * The {@code studentId} ↔ {@code student.id} nested-object projection is handled explicitly.
 */
@Mapper(componentModel = "spring")
public interface ProfilePersistenceMapper {

    /**
     * Converts a domain {@link Profile} to a {@link ProfileJpaEntity} for persistence.
     * Maps {@code studentId} to the nested {@code student.id} field.
     * Auditing fields are set by JPA and must not be overwritten.
     */
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProfileJpaEntity toJpaEntity(Profile profile);

    /**
     * Converts a {@link ProfileJpaEntity} from the database to a domain {@link Profile}.
     * Maps the nested {@code student.id} back to the flat {@code studentId} field.
     * {@code skills} and {@code version} are mapped by name.
     */
    @Mapping(target = "studentId", source = "student.id")
    Profile toDomain(ProfileJpaEntity entity);
}
