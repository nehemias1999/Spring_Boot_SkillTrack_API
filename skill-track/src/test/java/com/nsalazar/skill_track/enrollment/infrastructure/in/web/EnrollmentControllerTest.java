package com.nsalazar.skill_track.enrollment.infrastructure.in.web;

import com.nsalazar.skill_track.enrollment.application.port.in.EnrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.ListEnrollmentsByStudentUseCase;
import com.nsalazar.skill_track.enrollment.application.port.in.UnenrollStudentUseCase;
import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import com.nsalazar.skill_track.enrollment.infrastructure.in.web.mapper.EnrollmentWebMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
@Import(EnrollmentWebMapperImpl.class)
class EnrollmentControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private EnrollStudentUseCase enrollStudentUseCase;
    @MockitoBean private ListEnrollmentsByStudentUseCase listEnrollmentsByStudentUseCase;
    @MockitoBean private UnenrollStudentUseCase unenrollStudentUseCase;

    @Test
    void enroll_returns201() throws Exception {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID enrollmentId = UUID.randomUUID();

        when(enrollStudentUseCase.enrollStudent(studentId, courseId))
                .thenReturn(new Enrollment(enrollmentId, studentId, courseId, LocalDateTime.now()));

        mockMvc.perform(post("/api/v1/students/{studentId}/enrollments/{courseId}", studentId, courseId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(enrollmentId.toString()));
    }

    @Test
    void listEnrollments_returns200() throws Exception {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        when(listEnrollmentsByStudentUseCase.listEnrollmentsByStudent(studentId))
                .thenReturn(List.of(new Enrollment(UUID.randomUUID(), studentId, courseId, LocalDateTime.now())));

        mockMvc.perform(get("/api/v1/students/{studentId}/enrollments", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseId").value(courseId.toString()));
    }

    @Test
    void unenroll_returns204() throws Exception {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        doNothing().when(unenrollStudentUseCase).unenrollStudent(studentId, courseId);

        mockMvc.perform(delete("/api/v1/students/{studentId}/enrollments/{courseId}", studentId, courseId))
                .andExpect(status().isNoContent());

        verify(unenrollStudentUseCase).unenrollStudent(studentId, courseId);
    }
}
