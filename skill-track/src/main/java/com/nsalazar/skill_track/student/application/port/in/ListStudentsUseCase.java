package com.nsalazar.skill_track.student.application.port.in;

import com.nsalazar.skill_track.student.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Inbound port for listing students with pagination.
 */
public interface ListStudentsUseCase {
    /**
     * Returns a paginated list of all students.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of students
     */
    Page<Student> listStudents(Pageable pageable);
}
