package com.nsalazar.skill_track.instructor.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.instructor.domain.Address;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.AddressEmbeddable;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper that converts between the {@link Instructor} domain record and
 * the {@link InstructorJpaEntity} JPA entity.
 *
 * <p>MapStruct auto-generates the nested {@link Address} ↔ {@link AddressEmbeddable} conversion
 * because both types share the same field names ({@code street}, {@code city}, {@code country}).
 * This demonstrates {@code @Embedded} mapping: the value object is transparent to the mapper,
 * and the generated code is equivalent to mapping the three flat fields individually.
 */
@Mapper(componentModel = "spring")
public interface InstructorPersistenceMapper {

    /**
     * Converts a domain {@link Instructor} to an {@link InstructorJpaEntity} for persistence.
     * The {@code courses} collection is managed by JPA cascades and must not be overwritten here.
     */
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InstructorJpaEntity toJpaEntity(Instructor instructor);

    /**
     * Converts an {@link InstructorJpaEntity} from the database to a domain {@link Instructor}.
     * MapStruct auto-generates the {@link AddressEmbeddable} → {@link Address} conversion.
     */
    Instructor toDomain(InstructorJpaEntity entity);
}
