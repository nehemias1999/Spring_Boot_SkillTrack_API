package com.nsalazar.skill_track.student.infrastructure.out.persistence;

import com.nsalazar.skill_track.shared.AbstractPersistenceTest;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.infrastructure.out.persistence.mapper.StudentPersistenceMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@Import({StudentPersistenceAdapter.class, StudentPersistenceMapperImpl.class})
class StudentPersistenceAdapterTest extends AbstractPersistenceTest {

    @Autowired
    private StudentPersistenceAdapter adapter;

    @Test
    void save_andFindById_roundTrip() {
        Student student = new Student(null, "Alice", "Smith", "alice@example.com");
        Student saved = adapter.save(student);

        assertThat(saved.id()).isNotNull();
        assertThat(adapter.findById(saved.id())).isPresent()
                .get()
                .satisfies(s -> {
                    assertThat(s.firstName()).isEqualTo("Alice");
                    assertThat(s.email()).isEqualTo("alice@example.com");
                });
    }

    @Test
    void existsByEmail_returnsTrueWhenExists() {
        Student student = new Student(null, "Bob", "Jones", "bob@example.com");
        adapter.save(student);

        assertThat(adapter.existsByEmail("bob@example.com")).isTrue();
        assertThat(adapter.existsByEmail("nobody@example.com")).isFalse();
    }
}
