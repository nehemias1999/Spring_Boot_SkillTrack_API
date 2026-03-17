package com.nsalazar.skill_track.shared.exception;

/**
 * Exception thrown when a requested resource cannot be found in the system.
 * Maps to an HTTP 404 Not Found response via {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotFoundException} with the given detail message.
     *
     * @param message a description of the missing resource
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
