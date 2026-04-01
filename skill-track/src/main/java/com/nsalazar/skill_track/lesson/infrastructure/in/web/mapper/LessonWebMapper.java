package com.nsalazar.skill_track.lesson.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.lesson.application.port.in.CreateLessonUseCase.CreateLessonCommand;
import com.nsalazar.skill_track.lesson.domain.Lesson;
import com.nsalazar.skill_track.lesson.infrastructure.in.web.dto.CreateLessonRequest;
import com.nsalazar.skill_track.lesson.infrastructure.in.web.dto.LessonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

/**
 * MapStruct mapper for the lesson web layer.
 * Combines the path-variable {@code courseId} with the request body to build the command.
 */
@Mapper(componentModel = "spring")
public interface LessonWebMapper {

    /**
     * Builds a {@link CreateLessonCommand} from the path-variable course id and the request body.
     *
     * @param courseId the course id from the URL path
     * @param request  the request body DTO
     * @return the use-case command
     */
    @Mapping(target = "courseId", source = "courseId")
    CreateLessonCommand toCommand(UUID courseId, CreateLessonRequest request);

    /**
     * Converts a {@link Lesson} domain record to a {@link LessonResponse} DTO.
     *
     * @param lesson the domain lesson
     * @return the response DTO
     */
    LessonResponse toResponse(Lesson lesson);
}
