package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;

/**
 * Factory responsible for creating DailyJournal aggregates.
 */
public interface DailyJournalFactory {

    DailyJournal create(DogId dogId, LocalDate journalDate);
}
