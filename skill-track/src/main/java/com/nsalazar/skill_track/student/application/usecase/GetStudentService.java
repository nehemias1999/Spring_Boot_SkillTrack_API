package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.application.port.in.GetStudentUseCase;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service that handles the get-student use case.
 * Retrieves an existing student by id, throwing a not-found exception when absent.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetStudentService implements GetStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    /**
     * Retrieves a student by its unique identifier.
     *
     * @param id the id of the student to retrieve
     * @return the matching {@link Student}
     * @throws ResourceNotFoundException if no student exists with the given id
     */
    @Override
    public Student getStudentById(Long id) {
        log.info("Fetching student with id {}", id);
        Student student = studentRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student not found with id {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });
        log.info("Student with id {} retrieved successfully", id);
        return student;
    }
}
