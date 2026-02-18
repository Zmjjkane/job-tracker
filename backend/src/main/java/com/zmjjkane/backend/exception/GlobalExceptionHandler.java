package com.zmjjkane.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

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

    // MethodArgumentNotValidException表示controller方法参数校验失败了
    // 典型触发条件: 参数是@RequestBody绑定出来的对象/参数上有@Valid/对象字段有校验注解/校验不通过
    /**
     * Handles validation failures for request body objects annotated with @Valid.
     *
     * When a controller method parameter (e.g. @RequestBody JobApplication) is
     * annotated with @Valid and Bean Validation constraints (e.g. @NotBlank,
     * @NotNull) are violated, Spring throws MethodArgumentNotValidException
     * before the controller method body is executed.
     *
     * This handler extracts field-level validation errors from the exception's
     * BindingResult, converts them into a human-readable string, and returns a
     * standardized HTTP 400 (Bad Request) JSON response.
     *
     * Extraction flow:
     * MethodArgumentNotValidException
     *      → getBindingResult()
     *      → getFieldErrors()
     *      → FieldError (field name + validation message)
     *
     * Example field error:
     * company: company is required
     *
     * Returned JSON structure:
     * {
     *   "timestamp": "...",
     *   "status": 400,
     *   "error": "Bad Request",
     *   "message": "Validation failed for the given input.",
     *   "errors": "company: company is required, position: position is required",
     *   "path": "/api/job-applications"
     * }
     *
     * Notes:
     * - Validation occurs during request binding (before controller logic).
     * - This ensures invalid data never reaches the service or persistence layer.
     * - Provides consistent REST error semantics across the API.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "message", "Validation failed for the given input.",
                "errors", errors,
                "path", request.getRequestURI());
    }
}
