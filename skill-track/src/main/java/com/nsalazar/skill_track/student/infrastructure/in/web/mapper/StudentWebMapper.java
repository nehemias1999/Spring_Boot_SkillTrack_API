package com.nsalazar.skill_track.student.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase.CreateStudentCommand;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.CreateStudentRequest;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.StudentResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper that converts between the web layer DTOs and the application-layer
 * command/domain types for the student bounded context.
 */
@Mapper(componentModel = "spring")
public interface StudentWebMapper {

    /**
     * Converts a {@link CreateStudentRequest} into a {@link CreateStudentCommand}.
     *
     * @param request the inbound request DTO
     * @return the corresponding use-case command
     */
    CreateStudentCommand toCommand(CreateStudentRequest request);

    /**
     * Converts a {@link Student} domain object into a {@link StudentResponse} DTO.
     *
     * @param student the domain student
     * @return the response DTO suitable for serialization
     */
    StudentResponse toResponse(Student student);
}
