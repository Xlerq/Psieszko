package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository abstraction for DailyJournal aggregates.
 */
public interface DailyJournalRepository extends JpaRepository<DailyJournal, Long> {

    default Optional<DailyJournal> findForDogOnDate(DogId dogId, LocalDate journalDate) {
        return findByDogIdValueAndJournalDate(dogId.id(), journalDate);
    }

    default boolean existsForDogOnDate(DogId dogId, LocalDate journalDate) {
        return countByDogIdValueAndJournalDate(dogId.id(), journalDate) > 0;
    }

    @Query("""
            select journal
            from DailyJournal journal
            where journal.dogId.id = :dogId
              and journal.journalDate = :journalDate
            """)
    Optional<DailyJournal> findByDogIdValueAndJournalDate(
            @Param("dogId") Long dogId,
            @Param("journalDate") LocalDate journalDate
    );

    @Query("""
            select count(journal)
            from DailyJournal journal
            where journal.dogId.id = :dogId
              and journal.journalDate = :journalDate
            """)
    long countByDogIdValueAndJournalDate(
            @Param("dogId") Long dogId,
            @Param("journalDate") LocalDate journalDate
    );
}
