package edu.prz.psieszko.dailyjournal.application;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.dailyjournal.domain.Activity;
import edu.prz.psieszko.dailyjournal.domain.DailyJournal;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalFactoryImpl;
import edu.prz.psieszko.foundation.exception.GlobalExceptionHandler;
import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class DailyJournalControllerTest {

    private DailyJournalApplicationService dailyJournalApplicationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        dailyJournalApplicationService = mock(DailyJournalApplicationService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DailyJournalController(dailyJournalApplicationService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createsDailyJournalForDogFromPath() throws Exception {
        LocalDate journalDate = LocalDate.of(2026, 6, 2);
        DailyJournal dailyJournal = new DailyJournalFactoryImpl().create(new DogId(7L), journalDate);

        when(dailyJournalApplicationService.createDailyJournal(argThat(command ->
                command.dogId().equals(new DogId(7L)) && command.journalDate().equals(journalDate)
        ))).thenReturn(dailyJournal);

        mockMvc.perform(post("/api/dogs/7/daily-journals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"journalDate\":\"2026-06-02\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dogId").value(7))
                .andExpect(jsonPath("$.journalDate").value("2026-06-02"));

        verify(dailyJournalApplicationService).createDailyJournal(argThat(command ->
                command.dogId().equals(new DogId(7L)) && command.journalDate().equals(journalDate)
        ));
    }

    @Test
    void recordsActivityForDogDailyJournal() throws Exception {
        LocalDate journalDate = LocalDate.of(2026, 6, 2);
        LocalTime occurredAt = LocalTime.of(9, 15);
        DailyJournal dailyJournal = new DailyJournalFactoryImpl().create(new DogId(7L), journalDate);
        Activity activity = dailyJournal.addActivity(occurredAt, "Morning walk and social play");

        when(dailyJournalApplicationService.recordActivity(argThat(command ->
                command.dogId().equals(new DogId(7L))
                        && command.journalDate().equals(journalDate)
                        && command.occurredAt().equals(occurredAt)
                        && command.description().equals("Morning walk and social play")
        ))).thenReturn(activity);

        mockMvc.perform(post("/api/dogs/7/daily-journals/2026-06-02/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "occurredAt": "09:15:00",
                                  "description": "Morning walk and social play"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.occurredAt").value("09:15:00"))
                .andExpect(jsonPath("$.description").value("Morning walk and social play"));

        verify(dailyJournalApplicationService).recordActivity(argThat(command ->
                command.dogId().equals(new DogId(7L))
                        && command.journalDate().equals(journalDate)
                        && command.occurredAt().equals(occurredAt)
                        && command.description().equals("Morning walk and social play")
        ));
    }

    @Test
    void previewsDogDailyJournal() throws Exception {
        LocalDate journalDate = LocalDate.of(2026, 6, 2);
        DailyJournal dailyJournal = new DailyJournalFactoryImpl().create(new DogId(7L), journalDate);
        dailyJournal.addActivity(LocalTime.of(9, 15), "Morning walk and social play");

        when(dailyJournalApplicationService.previewDogDailyJournal(new DogId(7L), journalDate))
                .thenReturn(dailyJournal);

        mockMvc.perform(get("/api/dogs/7/daily-journals/2026-06-02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dogId").value(7))
                .andExpect(jsonPath("$.activities[0].description").value("Morning walk and social play"));
    }
}
