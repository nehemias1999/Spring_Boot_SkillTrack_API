package com.nsalazar.skill_track.enrollment.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaEntity;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.infrastructure.out.persistence.mapper.EnrollmentPersistenceMapperImpl;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import com.nsalazar.skill_track.shared.AbstractPersistenceTest;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@Import({EnrollmentPersistenceAdapter.class, EnrollmentPersistenceMapperImpl.class})
class EnrollmentPersistenceAdapterTest extends AbstractPersistenceTest {

    @Autowired
    private EnrollmentPersistenceAdapter adapter;

    @Autowired
    private TestEntityManager entityManager;

    private StudentJpaEntity student;
    private CourseJpaEntity course;

    @BeforeEach
    void setUp() {
        InstructorJpaEntity instructor = entityManager.persist(
                new InstructorJpaEntity(null, "Prof", "Smith", "prof@example.com", "bio", new ArrayList<>()));
        student = entityManager.persist(
                new StudentJpaEntity(null, "Tom", "Brown", "tom@example.com"));
        course = entityManager.persist(
                new CourseJpaEntity(null, "Spring Boot", "Learn Spring", BigDecimal.valueOf(49.99), instructor));
        entityManager.flush();
    }

    @Test
    void save_andExistsByStudentIdAndCourseId_roundTrip() {
        Enrollment enrollment = new Enrollment(null, student.getId(), course.getId(), LocalDateTime.now());
        Enrollment saved = adapter.save(enrollment);

        assertThat(saved.id()).isNotNull();
        assertThat(adapter.existsByStudentIdAndCourseId(student.getId(), course.getId())).isTrue();
        assertThat(adapter.existsByStudentIdAndCourseId(student.getId(), 9999L)).isFalse();
    }
}
