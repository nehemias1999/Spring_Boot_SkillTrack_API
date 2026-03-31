package com.nsalazar.skill_track.review.infrastructure.out.persistence.mapper;

import com.nsalazar.skill_track.review.domain.Review;
import com.nsalazar.skill_track.review.infrastructure.out.persistence.ReviewJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper between {@link Review} domain record and {@link ReviewJpaEntity}.
 */
@Mapper(componentModel = "spring")
public interface ReviewPersistenceMapper {

    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ReviewJpaEntity toJpaEntity(Review review);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "createdAt", source = "createdAt")
    Review toDomain(ReviewJpaEntity entity);
}
