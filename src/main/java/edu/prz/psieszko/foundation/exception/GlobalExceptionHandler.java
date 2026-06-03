package edu.prz.psieszko.foundation.exception;

import edu.prz.psieszko.foundation.application.ProblemResponse;
import edu.prz.psieszko.foundation.domain.DomainException;
import edu.prz.psieszko.foundation.domain.NotExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() == null
                                ? "Invalid value"
                                : fieldError.getDefaultMessage(),
                        (first, second) -> first
                ));

        return badRequest("Validation failed", request, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        Map<String, String> details = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (first, second) -> first
                ));

        return badRequest("Validation failed", request, details);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ProblemResponse> handleNotExists(
            NotExistsException exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ProblemResponse> handleDomainException(
            DomainException exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ProblemResponse> handleInvalidState(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemResponse> handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemResponse> handleNoSuchElement(
            NoSuchElementException exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemResponse> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request);
    }

    private ResponseEntity<ProblemResponse> badRequest(
            String message,
            HttpServletRequest request,
            Map<String, String> details
    ) {
        return ResponseEntity
                .badRequest()
                .body(ProblemResponse.of(HttpStatus.BAD_REQUEST, message, request.getRequestURI(), details));
    }

    private ResponseEntity<ProblemResponse> problem(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(status)
                .body(ProblemResponse.of(status, message, request.getRequestURI()));
    }
}
