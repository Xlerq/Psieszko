package edu.prz.psieszko.service.application;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.service.domain.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-reservation-api-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Test
    void createsReservationThroughApi() throws Exception {
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createReservationJson()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/reservations/\\d+")))
                .andExpect(jsonPath("$.dogId").value(7))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.payment.amount").value(250.00))
                .andExpect(jsonPath("$.payment.status").value("PENDING"));
    }

    @Test
    void getsReservationThroughApi() throws Exception {
        Long reservationId = createReservation();

        mockMvc.perform(get("/api/reservations/{id}", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId))
                .andExpect(jsonPath("$.dogId").value(7));
    }

    @Test
    void changesReservationDateThroughApi() throws Exception {
        Long reservationId = createReservation();

        mockMvc.perform(patch("/api/reservations/{id}/date", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "startDate": "2030-08-01T08:00:00",
                                  "endDate": "2030-08-05T16:00:00"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value("2030-08-01T08:00:00"))
                .andExpect(jsonPath("$.endDate").value("2030-08-05T16:00:00"));
    }

    @Test
    void marksPaymentAsPaidThroughApi() throws Exception {
        Long reservationId = createReservation();

        mockMvc.perform(post("/api/reservations/{id}/payment", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payment.status").value("PAID"));
    }

    @Test
    void cancelsReservationThroughApi() throws Exception {
        Long reservationId = createReservation();

        mockMvc.perform(post("/api/reservations/{id}/cancel", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void rejectsInvalidReservationDateOrder() throws Exception {
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dogId": 7,
                                  "startDate": "2030-07-05T16:00:00",
                                  "endDate": "2030-07-01T08:00:00",
                                  "paymentAmount": 250.00
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Start date must be before end date"));
    }

    private Long createReservation() throws Exception {
        String location = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createReservationJson()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    private String createReservationJson() {
        return """
                {
                  "dogId": 7,
                  "startDate": "2030-07-01T08:00:00",
                  "endDate": "2030-07-05T16:00:00",
                  "paymentAmount": 250.00
                }
                """;
    }
}
