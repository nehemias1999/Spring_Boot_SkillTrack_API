package com.nsalazar.skill_track.enrollment.application.usecase;

import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.port.out.CourseRepositoryPort;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.domain.port.out.EnrollmentRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollStudentServiceTest {

    @Mock
    private StudentRepositoryPort studentRepositoryPort;
    @Mock
    private CourseRepositoryPort courseRepositoryPort;
    @Mock
    private EnrollmentRepositoryPort enrollmentRepositoryPort;

    @InjectMocks
    private EnrollStudentService enrollStudentService;

    @Test
    void enrollStudent_happyPath_returnsEnrollment() {
        when(studentRepositoryPort.findById(1L))
                .thenReturn(Optional.of(new Student(1L, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(10L))
                .thenReturn(Optional.of(new Course(10L, "Java", "desc", BigDecimal.TEN, 5L)));
        when(enrollmentRepositoryPort.existsByStudentIdAndCourseId(1L, 10L)).thenReturn(false);
        Enrollment saved = new Enrollment(100L, 1L, 10L, LocalDateTime.now());
        when(enrollmentRepositoryPort.save(any())).thenReturn(saved);

        Enrollment result = enrollStudentService.enrollStudent(1L, 10L);

        assertThat(result.id()).isEqualTo(100L);
        verify(enrollmentRepositoryPort).save(any(Enrollment.class));
    }

    @Test
    void enrollStudent_studentNotFound_throwsResourceNotFoundException() {
        when(studentRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(99L, 10L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void enrollStudent_courseNotFound_throwsResourceNotFoundException() {
        when(studentRepositoryPort.findById(1L))
                .thenReturn(Optional.of(new Student(1L, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(1L, 99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void enrollStudent_duplicate_throwsBusinessValidationException() {
        when(studentRepositoryPort.findById(1L))
                .thenReturn(Optional.of(new Student(1L, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(10L))
                .thenReturn(Optional.of(new Course(10L, "Java", "desc", BigDecimal.TEN, 5L)));
        when(enrollmentRepositoryPort.existsByStudentIdAndCourseId(1L, 10L)).thenReturn(true);

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(1L, 10L))
                .isInstanceOf(BusinessValidationException.class);

        verify(enrollmentRepositoryPort, never()).save(any());
    }
}
