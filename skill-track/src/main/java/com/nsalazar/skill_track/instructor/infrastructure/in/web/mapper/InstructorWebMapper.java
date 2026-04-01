package com.nsalazar.skill_track.instructor.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase.CreateInstructorCommand;
import com.nsalazar.skill_track.instructor.domain.Address;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.AddressRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.AddressResponse;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.CreateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.InstructorResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper that converts between the web layer DTOs and the application-layer
 * command/domain types for the instructor bounded context.
 *
 * <p>MapStruct auto-generates the nested {@link AddressRequest} ↔ {@link Address} and
 * {@link Address} ↔ {@link AddressResponse} conversions because the field names match.
 */
@Mapper(componentModel = "spring")
public interface InstructorWebMapper {

    /**
     * Converts a {@link CreateInstructorRequest} into a {@link CreateInstructorCommand}.
     * The nested {@link AddressRequest} → {@link Address} conversion is auto-generated.
     */
    CreateInstructorCommand toCommand(CreateInstructorRequest request);

    /**
     * Converts an {@link Instructor} domain object into an {@link InstructorResponse} DTO.
     * The nested {@link Address} → {@link AddressResponse} conversion is auto-generated.
     */
    InstructorResponse toResponse(Instructor instructor);
}
