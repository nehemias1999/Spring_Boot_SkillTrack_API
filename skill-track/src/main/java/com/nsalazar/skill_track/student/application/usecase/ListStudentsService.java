package com.nsalazar.skill_track.student.application.usecase;

import com.nsalazar.skill_track.student.application.port.in.ListStudentsUseCase;
import com.nsalazar.skill_track.student.domain.Student;
import com.nsalazar.skill_track.student.domain.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for listing students with pagination.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudentsService implements ListStudentsUseCase {

    private final StudentRepositoryPort studentRepositoryPort;

    /**
     * Returns a paginated list of all students.
     *
     * @param pageable pagination parameters
     * @return a page of students
     */
    @Override
    public Page<Student> listStudents(Pageable pageable) {
        log.info("Listing students with pageable: {}", pageable);
        return studentRepositoryPort.findAll(pageable);
    }
}
