package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.mapper.CoursePersistenceMapperImpl;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.InstructorJpaEntity;
import com.nsalazar.skill_track.shared.AbstractPersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@Import({CoursePersistenceAdapter.class, CoursePersistenceMapperImpl.class})
class CoursePersistenceAdapterTest extends AbstractPersistenceTest {

    @Autowired
    private CoursePersistenceAdapter adapter;

    @Autowired
    private TestEntityManager entityManager;

    private InstructorJpaEntity instructor;

    @BeforeEach
    void setUp() {
        instructor = entityManager.persist(
                new InstructorJpaEntity(null, "Prof", "Smith", "prof@example.com", "bio", new ArrayList<>()));
        entityManager.flush();
    }

    @Test
    void save_andFindById_roundTrip() {
        Course course = new Course(null, "Spring Boot", "Learn Spring", BigDecimal.valueOf(49.99), instructor.getId());
        Course saved = adapter.save(course);

        assertThat(saved.id()).isNotNull();
        assertThat(adapter.findById(saved.id())).isPresent()
                .get()
                .satisfies(c -> {
                    assertThat(c.title()).isEqualTo("Spring Boot");
                    assertThat(c.price()).isEqualByComparingTo(BigDecimal.valueOf(49.99));
                    assertThat(c.instructorId()).isEqualTo(instructor.getId());
                });
    }
}
