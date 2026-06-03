package edu.prz.psieszko.ownercard.application;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.ownercard.domain.OwnerCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-owner-card-api-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class OwnerCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerCardRepository ownerCardRepository;

    @BeforeEach
    void setUp() {
        ownerCardRepository.deleteAll();
    }

    @Test
    void createsOwnerCardThroughApi() throws Exception {
        mockMvc.perform(post("/api/owner-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOwnerCardJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/owner-cards/\\d+")))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Kowalska"))
                .andExpect(jsonPath("$.dogIds.length()").value(0));
    }

    @Test
    void getsOwnerCardThroughApi() throws Exception {
        Long ownerCardId = createOwnerCard();

        mockMvc.perform(get("/api/owner-cards/{id}", ownerCardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ownerCardId))
                .andExpect(jsonPath("$.email").value("anna.kowalska@example.com"));
    }

    @Test
    void updatesOwnerCardContactThroughApi() throws Exception {
        Long ownerCardId = createOwnerCard();

        mockMvc.perform(patch("/api/owner-cards/{id}", ownerCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "phoneNumber": "111222333",
                                  "email": "anna.nowa@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("111222333"))
                .andExpect(jsonPath("$.email").value("anna.nowa@example.com"));
    }

    @Test
    void addsDogReferenceThroughApi() throws Exception {
        Long ownerCardId = createOwnerCard();

        mockMvc.perform(post("/api/owner-cards/{id}/dogs", ownerCardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dogId\": 12}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dogIds.length()").value(1))
                .andExpect(jsonPath("$.dogIds[0]").value(12));
    }

    @Test
    void rejectsInvalidOwnerCardRequest() throws Exception {
        mockMvc.perform(post("/api/owner-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private Long createOwnerCard() throws Exception {
        String location = mockMvc.perform(post("/api/owner-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOwnerCardJson()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    private String createOwnerCardJson() {
        return """
                {
                  "firstName": "Anna",
                  "lastName": "Kowalska",
                  "phoneNumber": "500600700",
                  "email": "anna.kowalska@example.com"
                }
                """;
    }
}
