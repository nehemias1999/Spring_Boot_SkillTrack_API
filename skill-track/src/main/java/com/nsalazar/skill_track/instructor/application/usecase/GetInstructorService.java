package com.nsalazar.skill_track.instructor.application.usecase;

import com.nsalazar.skill_track.instructor.application.port.in.GetInstructorUseCase;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import com.nsalazar.skill_track.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service that handles the get-instructor use case.
 * Retrieves an existing instructor by id, throwing a not-found exception when absent.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetInstructorService implements GetInstructorUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;

    /**
     * Retrieves an instructor by its unique identifier.
     *
     * @param id the id of the instructor to retrieve
     * @return the matching {@link Instructor}
     * @throws ResourceNotFoundException if no instructor exists with the given id
     */
    @Override
    public Instructor getInstructorById(UUID id) {
        log.info("Fetching instructor with id {}", id);
        Instructor instructor = instructorRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("Instructor not found with id {}", id);
                    return new ResourceNotFoundException("Instructor not found with id: " + id);
                });
        log.info("Instructor with id {} retrieved successfully", id);
        return instructor;
    }
}
