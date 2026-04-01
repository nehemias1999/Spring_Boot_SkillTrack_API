package com.nsalazar.skill_track.course.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase.CreateCourseCommand;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CourseResponse;
import com.nsalazar.skill_track.course.infrastructure.in.web.dto.CreateCourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

/**
 * MapStruct mapper that converts between the web layer DTOs and the application-layer
 * command/domain types for the course bounded context.
 */
@Mapper(componentModel = "spring")
public interface CourseWebMapper {

    /**
     * Builds a {@link CreateCourseCommand} by combining the path-variable instructor id
     * with the fields from the request body.
     *
     * @param instructorId the id extracted from the URL path
     * @param request      the request body DTO
     * @return the corresponding use-case command
     */
    @Mapping(target = "instructorId", source = "instructorId")
    @Mapping(target = "category", source = "request.category")
    @Mapping(target = "difficulty", source = "request.difficulty")
    @Mapping(target = "durationHours", source = "request.durationHours")
    @Mapping(target = "keywords", source = "request.keywords")
    CreateCourseCommand toCommand(UUID instructorId, CreateCourseRequest request);

    /**
     * Converts a {@link Course} domain object into a {@link CourseResponse} DTO.
     * All collection fields ({@code keywords}, {@code prerequisiteIds}, {@code tagNames}) map by name.
     *
     * @param course the domain course
     * @return the response DTO suitable for serialization
     */
    CourseResponse toResponse(Course course);
}
