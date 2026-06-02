package edu.prz.psieszko.foundation.application;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;

public record ProblemResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        Map<String, String> details
) {

    public static ProblemResponse of(HttpStatus status, String message, String path) {
        return of(status, message, path, Map.of());
    }

    public static ProblemResponse of(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> details
    ) {
        return new ProblemResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                LocalDateTime.now(),
                Map.copyOf(details)
        );
    }
}
