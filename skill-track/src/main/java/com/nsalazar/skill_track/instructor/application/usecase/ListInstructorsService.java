package com.nsalazar.skill_track.instructor.application.usecase;

import com.nsalazar.skill_track.instructor.application.port.in.ListInstructorsUseCase;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.instructor.domain.port.out.InstructorRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for listing instructors with pagination.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListInstructorsService implements ListInstructorsUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;

    /**
     * Returns a paginated list of all instructors.
     *
     * @param pageable pagination parameters
     * @return a page of instructors
     */
    @Override
    public Page<Instructor> listInstructors(Pageable pageable) {
        log.info("Listing instructors with pageable: {}", pageable);
        return instructorRepositoryPort.findAll(pageable);
    }
}
