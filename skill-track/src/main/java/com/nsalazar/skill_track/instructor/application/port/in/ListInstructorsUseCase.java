package com.nsalazar.skill_track.instructor.application.port.in;

import com.nsalazar.skill_track.instructor.domain.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Inbound port for listing instructors with pagination.
 */
public interface ListInstructorsUseCase {
    /**
     * Returns a paginated list of all instructors.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of instructors
     */
    Page<Instructor> listInstructors(Pageable pageable);
}
