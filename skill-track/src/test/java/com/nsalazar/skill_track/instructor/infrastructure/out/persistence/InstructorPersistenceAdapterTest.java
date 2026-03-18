package com.nsalazar.skill_track.instructor.infrastructure.out.persistence;

import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.out.persistence.mapper.InstructorPersistenceMapperImpl;
import com.nsalazar.skill_track.shared.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({InstructorPersistenceAdapter.class, InstructorPersistenceMapperImpl.class})
class InstructorPersistenceAdapterTest extends AbstractPersistenceTest {

    @Autowired
    private InstructorPersistenceAdapter adapter;

    @Test
    void save_andFindById_roundTrip() {
        Instructor instructor = new Instructor(null, "Alice", "Johnson", "alice@example.com", "Expert Java developer");
        Instructor saved = adapter.save(instructor);

        assertThat(saved.id()).isNotNull();
        assertThat(adapter.findById(saved.id())).isPresent()
                .get()
                .satisfies(i -> {
                    assertThat(i.firstName()).isEqualTo("Alice");
                    assertThat(i.email()).isEqualTo("alice@example.com");
                    assertThat(i.bio()).isEqualTo("Expert Java developer");
                });
    }

    @Test
    void existsByEmail_returnsTrueWhenExists() {
        Instructor instructor = new Instructor(null, "Bob", "Smith", "bob@example.com", "bio");
        adapter.save(instructor);

        assertThat(adapter.existsByEmail("bob@example.com")).isTrue();
        assertThat(adapter.existsByEmail("nobody@example.com")).isFalse();
    }
}
