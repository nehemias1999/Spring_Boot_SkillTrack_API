package com.nsalazar.skill_track.enrollment.application.port.in;

import com.nsalazar.skill_track.enrollment.domain.Enrollment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

/**
 * Input port for updating the progress percentage of an existing enrollment.
 * When progress reaches 100, the enrollment is automatically marked as {@code COMPLETED}.
 */
public interface UpdateEnrollmentProgressUseCase {

    /**
     * Command carrying the student id, course id, and the new progress value.
     *
     * @param studentId          the id of the student whose enrollment is being updated
     * @param courseId           the id of the course
     * @param progressPercentage the new progress value (0–100)
     */
    record UpdateEnrollmentProgressCommand(
            UUID studentId,
            UUID courseId,
            @Min(0) @Max(100) int progressPercentage
    ) {}

    /**
     * Updates the progress for the specified enrollment.
     *
     * @param command the progress update command
     * @return the updated {@link Enrollment}
     */
    Enrollment updateProgress(UpdateEnrollmentProgressCommand command);
}
