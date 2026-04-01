package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import com.nsalazar.skill_track.student.application.port.in.UpdateStudentUseCase;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Application service for partially updating a student.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class UpdateStudentService implements UpdateStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    /**
     * Fetches the existing student and applies only the non-null fields from the command.
     *
     * @param command the update command
     * @return the updated student
     */
    @Override
    public Student updateStudent(UpdateStudentCommand command) {
        log.info("Updating student with id '{}'", command.id());
        if (command.firstName() == null && command.lastName() == null && command.email() == null) {
            throw new BusinessValidationException("At least one field must be provided for update");
        }
        Student existing = studentRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + command.id()));

        Student updated = new Student(
                existing.id(),
                command.firstName() != null ? command.firstName() : existing.firstName(),
                command.lastName() != null ? command.lastName() : existing.lastName(),
                command.email() != null ? command.email() : existing.email(),
                existing.version()
        );
        Student saved = studentRepositoryPort.save(updated);
        log.info("Student with id '{}' updated successfully", saved.id());
        return saved;
    }
}
