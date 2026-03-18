package com.nsalazar.skill_track.instructor.application.usecase;

import com.nsalazar.skill_track.instructor.application.port.in.UpdateInstructorUseCase;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import com.nsalazar.skill_track.shared.exception.BusinessValidationException;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for partially updating an instructor.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateInstructorService implements UpdateInstructorUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;

    /**
     * Fetches the existing instructor and applies only the non-null fields from the command.
     *
     * @param command the update command
     * @return the updated instructor
     */
    @Override
    public Instructor updateInstructor(UpdateInstructorCommand command) {
        log.info("Updating instructor with id '{}'", command.id());
        if (command.firstName() == null && command.lastName() == null && command.email() == null && command.bio() == null) {
            throw new BusinessValidationException("At least one field must be provided for update");
        }
        Instructor existing = instructorRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + command.id()));

        Instructor updated = new Instructor(
                existing.id(),
                command.firstName() != null ? command.firstName() : existing.firstName(),
                command.lastName() != null ? command.lastName() : existing.lastName(),
                command.email() != null ? command.email() : existing.email(),
                command.bio() != null ? command.bio() : existing.bio()
        );
        Instructor saved = instructorRepositoryPort.save(updated);
        log.info("Instructor with id '{}' updated successfully", saved.id());
        return saved;
    }
}
