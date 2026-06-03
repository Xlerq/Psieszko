package edu.prz.psieszko.dogs.application;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.dogs.domain.dog.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-dog-api-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        dogRepository.deleteAll();
    }

    @Test
    void registersDogThroughApi() throws Exception {
        mockMvc.perform(post("/api/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDogJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/dogs/\\d+")))
                .andExpect(jsonPath("$.name").value("Burek"))
                .andExpect(jsonPath("$.breed").value("LABRADOR"))
                .andExpect(jsonPath("$.diet").value("STANDARD"))
                .andExpect(jsonPath("$.behavioralProfile.sociable").value(true))
                .andExpect(jsonPath("$.ownerCardId").value(3));
    }

    @Test
    void getsDogThroughApi() throws Exception {
        Long dogId = createDog();

        mockMvc.perform(get("/api/dogs/{id}", dogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dogId))
                .andExpect(jsonPath("$.animalTrait").value("FRIENDLY"));
    }

    @Test
    void changesDietThroughApi() throws Exception {
        Long dogId = createDog();

        mockMvc.perform(patch("/api/dogs/{id}/diet", dogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"diet\": \"GRAIN_FREE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diet").value("GRAIN_FREE"));
    }

    @Test
    void changesBehavioralProfileThroughApi() throws Exception {
        Long dogId = createDog();

        mockMvc.perform(patch("/api/dogs/{id}/behavioral-profile", dogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "aggressive": false,
                                  "sociable": false,
                                  "trained": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.behavioralProfile.sociable").value(false))
                .andExpect(jsonPath("$.behavioralProfile.trained").value(true));
    }

    @Test
    void rejectsInvalidDogRequest() throws Exception {
        mockMvc.perform(post("/api/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private Long createDog() throws Exception {
        String location = mockMvc.perform(post("/api/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDogJson()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    private String createDogJson() {
        return """
                {
                  "name": "Burek",
                  "breed": "LABRADOR",
                  "diet": "STANDARD",
                  "behavioralProfile": {
                    "aggressive": false,
                    "sociable": true,
                    "trained": true
                  },
                  "animalTrait": "FRIENDLY",
                  "ownerCardId": 3
                }
                """;
    }
}
