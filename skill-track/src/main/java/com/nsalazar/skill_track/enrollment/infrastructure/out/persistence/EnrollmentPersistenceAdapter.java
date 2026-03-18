package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.enrollment.infrastructure.out.persistence.mapper.EnrollmentPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Persistence adapter that implements {@link EnrollmentRepositoryPort} using Spring Data JPA.
 * Translates between the domain {@link Enrollment} model and the JPA entity layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnrollmentPersistenceAdapter implements EnrollmentRepositoryPort {

    private final EnrollmentJpaRepository enrollmentJpaRepository;
    private final EnrollmentPersistenceMapper mapper;

    /**
     * Persists an enrollment record to the database.
     *
     * @param enrollment the domain enrollment to save
     * @return the saved enrollment with any generated fields populated
     */
    @Override
    public Enrollment save(Enrollment enrollment) {
        log.debug("Saving enrollment for studentId {} in courseId {}",
                enrollment.studentId(), enrollment.courseId());
        return mapper.toDomain(enrollmentJpaRepository.save(mapper.toJpaEntity(enrollment)));
    }

    /**
     * Checks whether an enrollment record exists for the given student and course pair.
     *
     * @param studentId the id of the student
     * @param courseId  the id of the course
     * @return {@code true} if the student is already enrolled in the course, {@code false} otherwise
     */
    @Override
    public boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        log.debug("Checking enrollment existence for studentId {} and courseId {}", studentId, courseId);
        return enrollmentJpaRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
