package edu.prz.psieszko.health.application;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.health.domain.HealthCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-health-card-api-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class HealthCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HealthCardRepository healthCardRepository;

    @BeforeEach
    void setUp() {
        healthCardRepository.deleteAll();
    }

    @Test
    void createsHealthCardThroughApi() throws Exception {
        mockMvc.perform(post("/api/health-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dogId": 12
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/health-cards/\\d+")))
                .andExpect(jsonPath("$.dogId").value(12))
                .andExpect(jsonPath("$.veterinaryVisits.length()").value(0))
                .andExpect(jsonPath("$.vaccinations.length()").value(0))
                .andExpect(jsonPath("$.medicines.length()").value(0));
    }

    @Test
    void registersVeterinaryVisitThroughApi() throws Exception {
        Long healthCardId = createHealthCard();

        mockMvc.perform(post("/api/health-cards/{id}/veterinary-visits", healthCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "veterinarian": {
                                    "name": "Anna Wet",
                                    "licenseNumber": "PW-123"
                                  },
                                  "visitDate": "2030-01-10",
                                  "description": "Kontrola po szczepieniu",
                                  "recommendations": "Obserwowac apetyt"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.veterinaryVisits.length()").value(1))
                .andExpect(jsonPath("$.veterinaryVisits[0].veterinarian.name").value("Anna Wet"))
                .andExpect(jsonPath("$.veterinaryVisits[0].description").value("Kontrola po szczepieniu"));
    }

    @Test
    void replacesVaccinationsThroughApi() throws Exception {
        Long healthCardId = createHealthCard();

        mockMvc.perform(patch("/api/health-cards/{id}/vaccinations", healthCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "vaccinations": [
                                    {
                                      "name": "Wscieklizna",
                                      "vaccinationDate": "2030-01-10",
                                      "nextVaccinationDate": "2031-01-10"
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vaccinations.length()").value(1))
                .andExpect(jsonPath("$.vaccinations[0].name").value("Wscieklizna"));
    }

    @Test
    void replacesMedicinesThroughApi() throws Exception {
        Long healthCardId = createHealthCard();

        mockMvc.perform(patch("/api/health-cards/{id}/medicines", healthCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "medicines": [
                                    {
                                      "name": "Probiotyk",
                                      "dosage": "1 tabletka dziennie",
                                      "startDate": "2030-01-10",
                                      "endDate": "2030-01-17"
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicines.length()").value(1))
                .andExpect(jsonPath("$.medicines[0].name").value("Probiotyk"));
    }

    @Test
    void getsHealthCardByDogIdThroughApi() throws Exception {
        Long healthCardId = createHealthCard();

        mockMvc.perform(get("/api/health-cards/dog/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(healthCardId))
                .andExpect(jsonPath("$.dogId").value(12));
    }

    @Test
    void rejectsInvalidHealthCardRequest() throws Exception {
        mockMvc.perform(post("/api/health-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dogId": null
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private Long createHealthCard() throws Exception {
        String location = mockMvc.perform(post("/api/health-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dogId": 12
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }
}
