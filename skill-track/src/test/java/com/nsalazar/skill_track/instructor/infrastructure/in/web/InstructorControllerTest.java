package com.nsalazar.skill_track.instructor.infrastructure.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase.CreateInstructorCommand;
import com.nsalazar.skill_track.instructor.application.port.in.GetInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.ListInstructorsUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.UpdateInstructorUseCase;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.dto.CreateInstructorRequest;
import com.nsalazar.skill_track.instructor.infrastructure.in.web.mapper.InstructorWebMapperImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstructorController.class)
@Import(InstructorWebMapperImpl.class)
class InstructorControllerTest {

    @Autowired private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean private CreateInstructorUseCase createInstructorUseCase;
    @MockitoBean private GetInstructorUseCase getInstructorUseCase;
    @MockitoBean private ListInstructorsUseCase listInstructorsUseCase;
    @MockitoBean private UpdateInstructorUseCase updateInstructorUseCase;

    @Test
    void createInstructor_validRequest_returns201() throws Exception {
        UUID id = UUID.randomUUID();
        when(createInstructorUseCase.createInstructor(any(CreateInstructorCommand.class)))
                .thenReturn(new Instructor(id, "Jane", "Smith", "jane@example.com", "bio"));

        mockMvc.perform(post("/api/v1/instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateInstructorRequest("Jane", "Smith", "jane@example.com", "bio"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void listInstructors_returns200WithPage() throws Exception {
        UUID id = UUID.randomUUID();
        when(listInstructorsUseCase.listInstructors(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Instructor(id, "Jane", "Smith", "jane@example.com", "bio"))));

        mockMvc.perform(get("/api/v1/instructors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("jane@example.com"));
    }

    @Test
    void getInstructor_existingId_returns200() throws Exception {
        UUID id = UUID.randomUUID();
        when(getInstructorUseCase.getInstructorById(id))
                .thenReturn(new Instructor(id, "Jane", "Smith", "jane@example.com", "bio"));

        mockMvc.perform(get("/api/v1/instructors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }
}
