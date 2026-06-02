package edu.prz.psieszko.lesson.application;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.lesson.domain.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-lesson-api-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        lessonRepository.deleteAll();
    }

    @Test
    void createsLessonThroughApi() throws Exception {
        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createLessonJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/lessons/\\d+")))
                .andExpect(jsonPath("$.employeeId").value(7))
                .andExpect(jsonPath("$.topic.name").value("Podstawowe komendy"))
                .andExpect(jsonPath("$.learningZone.name").value("Sala A"))
                .andExpect(jsonPath("$.equipment.length()").value(2));
    }

    @Test
    void getsCreatedLessonThroughApi() throws Exception {
        Long lessonId = createLesson();

        mockMvc.perform(get("/api/lessons/{id}", lessonId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lessonId))
                .andExpect(jsonPath("$.employeeId").value(7));
    }

    @Test
    void changesLessonTopicThroughApi() throws Exception {
        Long lessonId = createLesson();

        mockMvc.perform(patch("/api/lessons/{id}/topic", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Socjalizacja",
                                  "description": "Praca w grupie"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic.name").value("Socjalizacja"))
                .andExpect(jsonPath("$.topic.description").value("Praca w grupie"));
    }

    @Test
    void rejectsInvalidLessonRequest() throws Exception {
        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employeeId": null,
                                  "topic": { "name": "" },
                                  "learningZone": { "name": "Sala A" },
                                  "startDate": "2030-07-01T10:00:00",
                                  "endDate": "2030-07-01T11:00:00"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private Long createLesson() throws Exception {
        String location = mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createLessonJson()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    private String createLessonJson() {
        return """
                {
                  "employeeId": 7,
                  "topic": {
                    "name": "Podstawowe komendy",
                    "description": "Siad i zostan"
                  },
                  "learningZone": {
                    "name": "Sala A",
                    "location": "Parter"
                  },
                  "equipment": [
                    { "name": "Kliker" },
                    { "name": "Mata" }
                  ],
                  "startDate": "2030-07-01T10:00:00",
                  "endDate": "2030-07-01T11:00:00"
                }
                """;
    }
}
