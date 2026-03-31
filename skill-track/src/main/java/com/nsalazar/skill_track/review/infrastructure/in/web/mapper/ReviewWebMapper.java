package com.nsalazar.skill_track.review.infrastructure.in.web.mapper;

import com.nsalazar.skill_track.review.application.port.in.CreateReviewUseCase.CreateReviewCommand;
import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.infrastructure.in.web.dto.CreateReviewRequest;
import com.nsalazar.skill_track.review.infrastructure.in.web.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

/**
 * MapStruct mapper between web DTOs and domain/command types for the review slice.
 */
@Mapper(componentModel = "spring")
public interface ReviewWebMapper {

    @Mapping(target = "studentId", source = "studentId")
    @Mapping(target = "courseId", source = "courseId")
    @Mapping(target = "rating", source = "request.rating")
    @Mapping(target = "comment", source = "request.comment")
    CreateReviewCommand toCommand(UUID studentId, UUID courseId, CreateReviewRequest request);

    ReviewResponse toResponse(Review review);
}
