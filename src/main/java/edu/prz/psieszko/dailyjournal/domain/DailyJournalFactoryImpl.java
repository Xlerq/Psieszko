package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DailyJournalFactoryImpl implements DailyJournalFactory {

    @Override
    public DailyJournal create(DogId dogId, LocalDate journalDate) {
        return new DailyJournal(dogId, journalDate);
    }
}
