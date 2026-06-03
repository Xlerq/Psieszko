package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.foundation.domain.DomainException;
import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;

public class DailyJournalException extends DomainException {

    private DailyJournalException(String message) {
        super(message);
    }

    public static DailyJournalException alreadyExists(DogId dogId, LocalDate journalDate) {
        return new DailyJournalException(
                "Daily journal already exists for dog " + dogId.id() + " on " + journalDate
        );
    }
}
