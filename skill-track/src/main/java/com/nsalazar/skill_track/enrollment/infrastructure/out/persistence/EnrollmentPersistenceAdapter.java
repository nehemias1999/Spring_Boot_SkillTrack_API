package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.enrollment.infrastructure.out.persistence.mapper.EnrollmentPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    /**
     * Returns all enrollments for the given student.
     *
     * @param studentId the student id
     * @return list of enrollments
     */
    @Override
    public List<Enrollment> findByStudentId(UUID studentId) {
        log.debug("Finding enrollments for studentId: {}", studentId);
        return enrollmentJpaRepository.findByStudentId(studentId).stream().map(mapper::toDomain).toList();
    }

    /**
     * Removes the enrollment for the given student and course.
     *
     * @param studentId the student id
     * @param courseId  the course id
     */
    @Override
    public void deleteByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        log.debug("Deleting enrollment for studentId: {} and courseId: {}", studentId, courseId);
        enrollmentJpaRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    /**
     * Retrieves the enrollment for the given student and course.
     *
     * @param studentId the student id
     * @param courseId  the course id
     * @return an {@link Optional} containing the enrollment if found
     */
    @Override
    public Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId) {
        log.debug("Finding enrollment for studentId: {} and courseId: {}", studentId, courseId);
        return enrollmentJpaRepository.findByStudentIdAndCourseId(studentId, courseId)
                .map(mapper::toDomain);
    }
}
