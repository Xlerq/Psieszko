# Konteksty DDD

Ten dokument mapuje diagramy analityczne z `docs/01-analiza` na aktualny uklad pakietow DDD w kodzie.

## Uzasadnienie architektury
- system jest dzielony na wyrazne czesci o jasno okreslonej odpowiedzialnosci,
- logika przypadkow uzycia trafia do warstwy `application`,
- logika dziedzinowa i centralne pojecia biznesowe trafiaja do warstwy `domain`.

## Struktura pakietow

Kazdy bounded context ma taka sama strukture na poziomie wysokim:

- `application` dla serwisow aplikacyjnych i orkiestracji przypadkow uzycia
- `domain` dla aggregate root, factory i repository

Wewnetrzny podzial pakietow w `domain` moze sie roznic, jesli wynika to z juz istniejacej struktury projektu.

## Mapa kontekstow

| Kontekst z analizy | Pakiet Java | Aggregate root | Glowne asocjacje | Factory | Repository |
| --- | --- | --- | --- | --- | --- |
| Karta Wlasciciela | `edu.prz.psieszko.ownercard` | `OwnerCard` | `Owner`, `Dog` | `OwnerCardFactory` | `OwnerCardRepository` |
| Dane Psa | `edu.prz.psieszko.dogs` | `Dog` | `OwnerCard`, `Breed`, `Diet`, `BehavioralProfile`, `AnimalTraits` | `DogFactory` | `DogRepository` |
| Zdrowie | `edu.prz.psieszko.health` | `HealthCard` | `Dog`, `Veterinarian`, `VeterinaryVisit`, `Vaccination`, `Medicine` | `HealthCardFactory` | `HealthCardRepository` |
| Usluga | `edu.prz.psieszko.service` | `Reservation` | `Dog`, `Lesson`, `Payment`, `VeterinaryVisit` | `ReservationFactory` | `ReservationRepository` |
| Lekcja | `edu.prz.psieszko.lesson` | `Lesson` | `Employee`, `Topic`, `LearningZone`, `TrainingEquipment` | `LessonFactory` | `LessonRepository` |
| Dziennik Dnia | `edu.prz.psieszko.dailyjournal` | `DailyJournal` | `Dog`, `Activity`, `Incident`, `Meal` | `DailyJournalFactory` | `DailyJournalRepository` |
| Struktura Przedszkola | `edu.prz.psieszko.kindergartenstructure` | `Kindergarten` | `Employee`, `Role` | `KindergartenFactory` | `KindergartenRepository` |
