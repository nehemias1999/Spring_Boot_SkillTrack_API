package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.application.port.in.DeleteStudentUseCase;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for deleting a student.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeleteStudentService implements DeleteStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    /**
     * Verifies the student exists then deletes it.
     *
     * @param id the student id
     */
    @Override
    public void deleteStudent(UUID id) {
        log.info("Deleting student with id '{}'", id);
        studentRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepositoryPort.deleteById(id);
        log.info("Student with id '{}' deleted successfully", id);
    }
}
