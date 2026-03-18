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
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollStudentServiceTest {

    @Mock private StudentRepositoryPort studentRepositoryPort;
    @Mock private CourseRepositoryPort courseRepositoryPort;
    @Mock private EnrollmentRepositoryPort enrollmentRepositoryPort;

    @InjectMocks private EnrollStudentService enrollStudentService;

    @Test
    void enrollStudent_happyPath_returnsEnrollment() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();

        when(studentRepositoryPort.findById(studentId))
                .thenReturn(Optional.of(new Student(studentId, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(courseId))
                .thenReturn(Optional.of(new Course(courseId, "Java", "desc", BigDecimal.TEN, instructorId)));
        when(enrollmentRepositoryPort.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(false);
        Enrollment saved = new Enrollment(enrollmentId, studentId, courseId, LocalDateTime.now());
        when(enrollmentRepositoryPort.save(any())).thenReturn(saved);

        Enrollment result = enrollStudentService.enrollStudent(studentId, courseId);

        assertThat(result.id()).isEqualTo(enrollmentId);
        verify(enrollmentRepositoryPort).save(any(Enrollment.class));
    }

    @Test
    void enrollStudent_studentNotFound_throwsResourceNotFoundException() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(studentRepositoryPort.findById(studentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(studentId, courseId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(studentId.toString());
    }

    @Test
    void enrollStudent_courseNotFound_throwsResourceNotFoundException() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();

        when(studentRepositoryPort.findById(studentId))
                .thenReturn(Optional.of(new Student(studentId, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(courseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(studentId, courseId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(courseId.toString());
    }

    @Test
    void enrollStudent_duplicate_throwsBusinessValidationException() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();

        when(studentRepositoryPort.findById(studentId))
                .thenReturn(Optional.of(new Student(studentId, "John", "Doe", "john@example.com")));
        when(courseRepositoryPort.findById(courseId))
                .thenReturn(Optional.of(new Course(courseId, "Java", "desc", BigDecimal.TEN, instructorId)));
        when(enrollmentRepositoryPort.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(true);

        assertThatThrownBy(() -> enrollStudentService.enrollStudent(studentId, courseId))
                .isInstanceOf(BusinessValidationException.class);

        verify(enrollmentRepositoryPort, never()).save(any());
    }
}
