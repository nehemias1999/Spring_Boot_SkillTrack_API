package com.nsalazar.skill_track.instructor.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase.CreateInstructorCommand;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.CreateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.InstructorResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper that converts between the web layer DTOs and the application-layer
 * command/domain types for the instructor bounded context.
 */
@Mapper(componentModel = "spring")
public interface InstructorWebMapper {

    /**
     * Converts a {@link CreateInstructorRequest} into a {@link CreateInstructorCommand}.
     *
     * @param request the inbound request DTO
     * @return the corresponding use-case command
     */
    CreateInstructorCommand toCommand(CreateInstructorRequest request);

    /**
     * Converts an {@link Instructor} domain object into an {@link InstructorResponse} DTO.
     *
     * @param instructor the domain instructor
     * @return the response DTO suitable for serialization
     */
    InstructorResponse toResponse(Instructor instructor);
}
