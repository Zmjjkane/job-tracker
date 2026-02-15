package com.zmjjkane.backend.exception;

/**
 * Thrown when a requested resource does not exist in database
 * Example: GET /job-applications/999 where id = 999 not found.
 */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
