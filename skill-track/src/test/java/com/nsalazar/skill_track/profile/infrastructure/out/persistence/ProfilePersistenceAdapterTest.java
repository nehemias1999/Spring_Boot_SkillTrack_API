package com.nsalazar.skill_track.profile.infrastructure.out.persistence;

import com.nsalazar.skill_track.profile.domain.Profile;
import com.nsalazar.skill_track.profile.infrastructure.out.persistence.mapper.ProfilePersistenceMapperImpl;
import com.nsalazar.skill_track.shared.AbstractPersistenceTest;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.StudentJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ProfilePersistenceAdapter.class, ProfilePersistenceMapperImpl.class})
class ProfilePersistenceAdapterTest extends AbstractPersistenceTest {

    @Autowired
    private ProfilePersistenceAdapter adapter;

    @Autowired
    private TestEntityManager entityManager;

    private StudentJpaEntity student;

    @BeforeEach
    void setUp() {
        student = entityManager.persist(
                new StudentJpaEntity(null, "Tom", "Brown", "tom@example.com"));
        entityManager.flush();
    }

    @Test
    void save_andFindByStudentId_roundTrip() {
        Profile profile = new Profile(null, student.getId(), "Bio text", "https://linkedin.com/in/tom", "+1234567890");
        Profile saved = adapter.save(profile);

        assertThat(saved.id()).isNotNull();
        assertThat(adapter.findByStudentId(student.getId())).isPresent()
                .get()
                .satisfies(p -> {
                    assertThat(p.bio()).isEqualTo("Bio text");
                    assertThat(p.linkedInUrl()).isEqualTo("https://linkedin.com/in/tom");
                    assertThat(p.studentId()).isEqualTo(student.getId());
                });
    }
}
