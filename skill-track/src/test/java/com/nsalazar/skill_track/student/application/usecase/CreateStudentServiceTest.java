package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase.CreateStudentCommand;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateStudentServiceTest {

    @Mock
    private StudentRepositoryPort studentRepositoryPort;

    @InjectMocks
    private CreateStudentService createStudentService;

    @Test
    void createStudent_happyPath_returnsCreatedStudent() {
        CreateStudentCommand command = new CreateStudentCommand("John", "Doe", "john@example.com");
        UUID generatedId = UUID.randomUUID();
        Student saved = new Student(generatedId, "John", "Doe", "john@example.com");

        when(studentRepositoryPort.existsByEmail("john@example.com")).thenReturn(false);
        when(studentRepositoryPort.save(any())).thenReturn(saved);

        Student result = createStudentService.createStudent(command);

        assertThat(result.id()).isEqualTo(generatedId);
        assertThat(result.email()).isEqualTo("john@example.com");
        verify(studentRepositoryPort).save(any(Student.class));
    }

    @Test
    void createStudent_duplicateEmail_throwsBusinessValidationException() {
        CreateStudentCommand command = new CreateStudentCommand("John", "Doe", "john@example.com");

        when(studentRepositoryPort.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> createStudentService.createStudent(command))
                .isInstanceOf(BusinessValidationException.class)
                .hasMessageContaining("john@example.com");

        verify(studentRepositoryPort, never()).save(any());
    }
}
