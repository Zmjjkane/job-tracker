package com.zmjjkane.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Global REST exception handler.
 * Converts Java exceptions into standardized JSON HTTP responses.
 * RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * ControllerAdvice: globally effective for all Controller
 * ResponseBody serialize the return into a JSON object
 *
 * When Spring initializes, this handler is registered
 * Each time a controller throws an exception, enter this handler to
 * match handle function, and return is turned into JSON automatically
 *
 * Standard workflow:
 * 1. Client call controller
 * 2. controller call service
 * 3. service detects a problem (eg. id not exist)
 * 4. service throws an exception
 * 5. the exception return to controller
 * 6. Spring detects the exception and give it to RestController Advice
 * 7. handler returns JSON + 404
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ExceptionHandler means each time a ResourceNotFoundException is thrown,
    // Spring will call this function to handle it.
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request){
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
    }
}
