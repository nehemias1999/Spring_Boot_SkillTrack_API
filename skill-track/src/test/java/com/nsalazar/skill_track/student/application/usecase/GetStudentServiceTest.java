package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetStudentServiceTest {

    @Mock
    private StudentRepositoryPort studentRepositoryPort;

    @InjectMocks
    private GetStudentService getStudentService;

    @Test
    void getStudentById_found_returnsStudent() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "Jane", "Doe", "jane@example.com");
        when(studentRepositoryPort.findById(id)).thenReturn(Optional.of(student));

        Student result = getStudentService.getStudentById(id);

        assertThat(result).isEqualTo(student);
    }

    @Test
    void getStudentById_notFound_throwsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(studentRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getStudentService.getStudentById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
