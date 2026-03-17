package com.nsalazar.skill_track.enrollment.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.dto.EnrollmentResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper that converts between the {@link Enrollment} domain record and
 * the {@link EnrollmentResponse} web DTO.
 */
@Mapper(componentModel = "spring")
public interface EnrollmentWebMapper {

    /**
     * Converts an {@link Enrollment} domain object into an {@link EnrollmentResponse} DTO.
     *
     * @param enrollment the domain enrollment
     * @return the response DTO suitable for serialization
     */
    EnrollmentResponse toResponse(Enrollment enrollment);
}
