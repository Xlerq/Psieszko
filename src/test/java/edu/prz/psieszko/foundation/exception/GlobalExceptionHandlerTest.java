package edu.prz.psieszko.foundation.exception;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.foundation.domain.DomainException;
import edu.prz.psieszko.foundation.domain.NotExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handlesMethodArgumentNotValidAsBadRequest() throws Exception {
        mockMvc.perform(post("/test/validation/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/test/validation/body"))
                .andExpect(jsonPath("$.details.name").exists());
    }

    @Test
    void handlesConstraintViolationAsBadRequest() throws Exception {
        mockMvc.perform(get("/test/validation/constraint"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.details.value").exists());
    }

    @Test
    void handlesNotExistsExceptionAsNotFound() throws Exception {
        mockMvc.perform(get("/test/errors/not-exists"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Owner card does not exist"));
    }

    @Test
    void handlesDomainExceptionAsBadRequest() throws Exception {
        mockMvc.perform(get("/test/errors/domain"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Domain rule violated"));
    }

    @Test
    void handlesEntityNotFoundExceptionAsNotFound() throws Exception {
        mockMvc.perform(get("/test/errors/entity-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Entity not found"));
    }

    @Test
    void handlesNoSuchElementExceptionAsNotFound() throws Exception {
        mockMvc.perform(get("/test/errors/no-such-element"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Element not found"));
    }

    @Test
    void handlesExceptionAsInternalServerErrorWithoutTechnicalDetails() throws Exception {
        mockMvc.perform(get("/test/errors/server"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Internal server error"))
                .andExpect(jsonPath("$.message", not(containsString("database password"))));
    }

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @PostMapping("/validation/body")
        void validateBody(@Valid @RequestBody TestRequest request) {
        }

        @GetMapping("/validation/constraint")
        void validateConstraint() {
            var validator = Validation.buildDefaultValidatorFactory().getValidator();
            var violations = validator.validate(new ParamRequest(0));
            throw new ConstraintViolationException(violations);
        }

        @GetMapping("/errors/not-exists")
        void notExists() {
            throw NotExistsException.of("Owner card does not exist");
        }

        @GetMapping("/errors/domain")
        void domain() {
            throw new TestDomainException("Domain rule violated");
        }

        @GetMapping("/errors/entity-not-found")
        void entityNotFound() {
            throw new EntityNotFoundException("Entity not found");
        }

        @GetMapping("/errors/no-such-element")
        void noSuchElement() {
            throw new NoSuchElementException("Element not found");
        }

        @GetMapping("/errors/server")
        void server() {
            throw new RuntimeException("database password leaked in stack trace");
        }
    }

    record TestRequest(@NotBlank String name) {
    }

    record ParamRequest(@Positive Integer value) {
    }

    static class TestDomainException extends DomainException {

        TestDomainException(String message) {
            super(message);
        }
    }
}
