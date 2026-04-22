# Konteksty DDD

Ten dokument mapuje diagramy analityczne z `docs/01-analiza` na aktualny uklad pakietow DDD w kodzie.

## Uzasadnienie architektury

Przyjety podzial jest zgodny z materialami z wykladu o architekturze aplikacji korporacyjnych:

- system jest dzielony na wyrazne czesci o jasno okreslonej odpowiedzialnosci,
- logika przypadkow uzycia trafia do warstwy `application`,
- logika dziedzinowa i centralne pojecia biznesowe trafiaja do warstwy `domain`.

Podejscie jest tez spojne z przykladowym projektem prowadzacego, w ktorym nalezy wskazac centralne pojecia modelu, ich asocjacje oraz przygotowac model projektowy w stylu DDD.

## Struktura pakietow

Kazdy bounded context ma taka sama strukture:

- `application` dla serwisow aplikacyjnych i orkiestracji przypadkow uzycia
- `domain` dla aggregate root, factory i repository

## Mapa kontekstow

| Kontekst z analizy | Pakiet Java | Aggregate root | Glowne asocjacje | Factory | Repository |
| --- | --- | --- | --- | --- | --- |
| Karta Wlasciciela | `edu.prz.psieszko.ownercard` | `OwnerCard` | `Owner`, `Dog` | `OwnerCardFactory` | `OwnerCardRepository` |
| Dane Psa | `edu.prz.psieszko.dog` | `Dog` | `OwnerCard`, `Breed`, `Diet`, `BehavioralProfile`, `AnimalTraits` | `DogFactory` | `DogRepository` |
| Zdrowie | `edu.prz.psieszko.health` | `HealthCard` | `Dog`, `Veterinarian`, `VeterinaryVisit`, `Vaccination`, `Medicine` | `HealthCardFactory` | `HealthCardRepository` |
| Usluga | `edu.prz.psieszko.service` | `Reservation` | `Dog`, `Lesson`, `Payment`, `VeterinaryVisit` | `ReservationFactory` | `ReservationRepository` |
| Lekcja | `edu.prz.psieszko.lesson` | `Lesson` | `Employee`, `Topic`, `LearningZone`, `TrainingEquipment` | `LessonFactory` | `LessonRepository` |
| Dziennik Dnia | `edu.prz.psieszko.dailyjournal` | `DailyJournal` | `Dog`, `Activity`, `Incident`, `Meal` | `DailyJournalFactory` | `DailyJournalRepository` |
| Struktura Przedszkola | `edu.prz.psieszko.kindergartenstructure` | `Kindergarten` | `Employee`, `Role` | `KindergartenFactory` | `KindergartenRepository` |
