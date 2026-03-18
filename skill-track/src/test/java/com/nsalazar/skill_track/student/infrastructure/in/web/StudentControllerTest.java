package com.nsalazar.skill_track.student.infrastructure.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase.CreateStudentCommand;
import com.nsalazar.skill_track.student.application.port.in.DeleteStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.GetStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.ListStudentsUseCase;
import com.nsalazar.skill_track.student.application.port.in.UpdateStudentUseCase;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.infrastructure.in.web.dto.CreateStudentRequest;
import com.nsalazar.skill_track.student.infrastructure.in.web.mapper.StudentWebMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@Import(StudentWebMapperImpl.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean private CreateStudentUseCase createStudentUseCase;
    @MockitoBean private GetStudentUseCase getStudentUseCase;
    @MockitoBean private ListStudentsUseCase listStudentsUseCase;
    @MockitoBean private UpdateStudentUseCase updateStudentUseCase;
    @MockitoBean private DeleteStudentUseCase deleteStudentUseCase;

    @Test
    void createStudent_validRequest_returns201() throws Exception {
        UUID id = UUID.randomUUID();
        when(createStudentUseCase.createStudent(any(CreateStudentCommand.class)))
                .thenReturn(new Student(id, "John", "Doe", "john@example.com"));

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateStudentRequest("John", "Doe", "john@example.com"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createStudent_invalidRequest_returns422() throws Exception {
        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateStudentRequest("", "", "not-an-email"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStudent_existingId_returns200() throws Exception {
        UUID id = UUID.randomUUID();
        when(getStudentUseCase.getStudentById(id))
                .thenReturn(new Student(id, "Jane", "Doe", "jane@example.com"));

        mockMvc.perform(get("/api/v1/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void listStudents_returns200WithPage() throws Exception {
        UUID id = UUID.randomUUID();
        when(listStudentsUseCase.listStudents(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Student(id, "Alice", "Smith", "alice@example.com"))));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));
    }

    @Test
    void deleteStudent_existingId_returns204() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(deleteStudentUseCase).deleteStudent(id);

        mockMvc.perform(delete("/api/v1/students/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteStudentUseCase).deleteStudent(id);
    }
}
