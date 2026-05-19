# Konteksty DDD i agregaty — Psie Przedszkole

Ten dokument opisuje aktualny podział projektu na bounded contexty oraz stan najważniejszych agregatów. Opis jest zgodny z obecnym układem pakietów w kodzie.

---

## Mapa bounded contextów

System jest podzielony na następujące bounded contexty:

| Bounded context | Pakiet Java | Główny agregat | Aktualny stan |
| --- | --- | --- | --- |
| Karta właściciela | `edu.prz.psieszko.ownercard` | `OwnerCard` | pełny agregat |
| Dane psa | `edu.prz.psieszko.dogs` | `Dog` | pełny agregat |
| Usługa / rezerwacje | `edu.prz.psieszko.service` | `Reservation` | pełny agregat |
| Zdrowie | `edu.prz.psieszko.health` | `HealthCard` | szkielet / podstawowy agregat |
| Lekcje | `edu.prz.psieszko.lesson` | `Lesson` | szkielet / podstawowy agregat |
| Dziennik dnia | `edu.prz.psieszko.dailyjournal` | `DailyJournal` | szkielet / podstawowy agregat |
| Struktura przedszkola | `edu.prz.psieszko.kindergartenstructure` | `Kindergarten` | częściowo zaimplementowany |

```text
┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│      ownercard       │   │         dogs         │   │        service       │
│                      │   │                      │   │                      │
│  OwnerCard           │   │  Dog                 │   │  Reservation         │
│  Owner               │   │  Breed, Diet         │   │  Payment             │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘

┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│ kindergartenstructure│   │     dailyjournal     │   │        health        │
│                      │   │                      │   │                      │
│  Kindergarten        │   │  DailyJournal        │   │  HealthCard          │
│  Employee, Role      │   │                      │   │                      │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘

                        ┌──────────────────────┐
                        │        lesson        │
                        │                      │
                        │  Lesson              │
                        └──────────────────────┘

                  Shared Kernel: typowane identyfikatory
                  Foundation: klasy bazowe i wspólne kontrakty
```

---

## Foundation

Pakiet: `edu.prz.psieszko.foundation.domain`

Foundation zawiera wspólne elementy używane w różnych kontekstach:

- `Identity`,
- `AuditableEntity`,
- `BaseEntity`,
- `StandardFactory`,
- `DomainException`,
- `NotExistsException`.

Szczegółowy opis Foundation znajduje się w `docs/02-projekt/architektura.md`.

---

## Shared Kernel

Pakiet: `edu.prz.psieszko.shared.identity`

Shared Kernel zawiera typowane identyfikatory wykorzystywane do łączenia agregatów bez tworzenia bezpośrednich referencji obiektowych między nimi:

- `DogId`,
- `EmployeeId`,
- `HealthCardId`,
- `LessonId`,
- `OwnerCardId`,
- `ReservationId`.

Przykłady użycia:

- `Dog` przechowuje `OwnerCardId`, czyli identyfikator karty właściciela.
- `Reservation` przechowuje `DogId`, czyli identyfikator psa przypisanego do rezerwacji.

Dzięki temu agregaty zachowują wyraźniejsze granice i nie są silnie połączone przez bezpośrednie referencje do obiektów z innych kontekstów.

---

## Agregaty

### OwnerCard — karta właściciela

**Pakiet:** `edu.prz.psieszko.ownercard`

**Status:** pełny agregat.

**Aggregate root:** `OwnerCard`

**Elementy modelu:**

- `OwnerCard`,
- `Owner`,
- `OwnerCardFactory`,
- `OwnerCardRepository`,
- serwis aplikacyjny,
- kontroler REST.

`OwnerCard` reprezentuje kartę właściciela psa. Agregat zawiera dane właściciela oraz powiązania z psami. Powiązania z psami powinny być realizowane przez typowane identyfikatory, a nie przez bezpośrednie trzymanie obiektów `Dog`.

**Znaczenie biznesowe:**

Ten agregat odpowiada za dane właściciela oraz powiązanie właściciela z jego psami.

---

### Dog — pies

**Pakiet:** `edu.prz.psieszko.dogs.domain.dog`

**Status:** pełny agregat.

**Aggregate root:** `Dog`

**Elementy modelu:**

- `Dog`,
- `Breed`,
- `Diet`,
- `BehavioralProfile`,
- `AnimalTrait`,
- `DogFactory`,
- `DogFactoryImpl`,
- `DogRepository`.

**Najważniejsze pola agregatu `Dog`:**

- `name` — imię psa,
- `breed` — rasa psa,
- `diet` — dieta psa,
- `behavioralProfile` — profil zachowania psa,
- `animalTrait` — cecha zwierzęcia,
- `ownerCardId` — identyfikator karty właściciela (`OwnerCardId`).

`Dog` nie jest tylko prostym obiektem z polami `id` i `name`. Aktualna implementacja zawiera także rasę, dietę, profil zachowania, cechę zwierzęcia oraz powiązanie z kartą właściciela przez `OwnerCardId`.

**Znaczenie biznesowe:**

Agregat `Dog` opisuje dane psa potrzebne do obsługi psiego przedszkola, w tym informacje o właścicielu, zachowaniu i podstawowych cechach psa.

---

### Reservation — rezerwacja

**Pakiet:** `edu.prz.psieszko.service.domain`

**Status:** pełny agregat.

**Aggregate root:** `Reservation`

**Elementy modelu:**

- `Reservation`,
- `Payment`,
- `ReservationStatus`,
- `PaymentStatus`,
- `ReservationFactory`,
- `ReservationFactoryImpl`,
- `ReservationRepository`,
- serwis aplikacyjny.

**Najważniejsze pola agregatu `Reservation`:**

- `dogId` — identyfikator psa (`DogId`),
- `startDate` — data i godzina rozpoczęcia rezerwacji,
- `endDate` — data i godzina zakończenia rezerwacji,
- `status` — status rezerwacji,
- `payment` — płatność osadzona w rezerwacji.

**Najważniejsze zachowania agregatu:**

- `changeDate(newStartDate, newEndDate)` — zmiana terminu rezerwacji z walidacją dat i statusu,
- `markPaymentAsPaid()` — oznaczenie płatności jako opłaconej,
- `cancel()` — anulowanie rezerwacji z kontrolą statusu.

`Reservation` nie jest już szkieletem. Aktualnie posiada logikę biznesową, płatność, statusy, fabrykę, repozytorium i serwis aplikacyjny.

**Znaczenie biznesowe:**

Agregat `Reservation` odpowiada za zapisanie rezerwacji pobytu/usługi dla psa oraz kontrolę jej terminu, statusu i płatności.

---

### HealthCard — karta zdrowia

**Pakiet:** `edu.prz.psieszko.health`

**Status:** szkielet / podstawowy agregat.

**Aggregate root:** `HealthCard`

**Planowana odpowiedzialność:**

Agregat ma odpowiadać za informacje zdrowotne psa, np. wizyty weterynaryjne, szczepienia, leki i dane lekarza weterynarii.

---

### Lesson — zajęcia

**Pakiet:** `edu.prz.psieszko.lesson`

**Status:** szkielet / podstawowy agregat.

**Aggregate root:** `Lesson`

**Planowana odpowiedzialność:**

Agregat ma odpowiadać za zajęcia realizowane w psim przedszkolu, np. temat zajęć, strefę nauki, sprzęt treningowy i prowadzącego pracownika.

---

### DailyJournal — dziennik dnia

**Pakiet:** `edu.prz.psieszko.dailyjournal`

**Status:** szkielet / podstawowy agregat.

**Aggregate root:** `DailyJournal`

**Planowana odpowiedzialność:**

Agregat ma odpowiadać za dzienny zapis informacji o psie, np. aktywności, incydenty i posiłki.

---

### Kindergarten — struktura przedszkola

**Pakiet:** `edu.prz.psieszko.kindergartenstructure`

**Status:** częściowo zaimplementowany agregat.

**Aggregate root:** `Kindergarten`

**Elementy modelu:**

- `Kindergarten`,
- `Employee`,
- `Role`,
- `KindergartenFactory`,
- `KindergartenRepository`,
- serwis aplikacyjny.

`Kindergarten` ma encje wewnętrzne `Employee` i `Role`, ale nie należy go oznaczać jako w pełni ukończony kontekst, ponieważ aktualny serwis aplikacyjny nie zawiera pełnych przypadków użycia, a kontekst nie posiada kompletnej warstwy REST.

**Agregat z więcej niż jedną encją:**

`Kindergarten` jest agregatem, który ma więcej niż jedną encję wewnętrzną: `Employee` oraz `Role`.

---

## Podsumowanie agregatów

| Agregat | Kontekst | Status | Elementy wewnętrzne / powiązane | Więcej niż jedna encja wewnętrzna? |
| --- | --- | --- | --- | --- |
| `OwnerCard` | `ownercard` | pełny | `Owner` | nie |
| `Dog` | `dogs` | pełny | `Breed`, `Diet`, `BehavioralProfile`, `AnimalTrait`, `OwnerCardId` | nie |
| `Reservation` | `service` | pełny | `Payment`, `ReservationStatus`, `PaymentStatus`, `DogId` | nie |
| `Kindergarten` | `kindergartenstructure` | częściowy | `Employee`, `Role` | tak |
| `HealthCard` | `health` | szkielet / podstawowy | planowane dane zdrowotne | nieustalone |
| `Lesson` | `lesson` | szkielet / podstawowy | planowane dane zajęć | nieustalone |
| `DailyJournal` | `dailyjournal` | szkielet / podstawowy | planowane wpisy dzienne | nieustalone |

---

## Wniosek projektowy

Projekt posiada wspólne elementy Foundation, Shared Kernel z typowanymi identyfikatorami oraz kilka agregatów zgodnych z podejściem DDD. Najważniejsze pełne agregaty wymagane w zadaniu to:

- `OwnerCard`,
- `Dog`,
- `Reservation`.

Dodatkowo `Kindergarten` pokazuje przykład agregatu z więcej niż jedną encją wewnętrzną (`Employee` i `Role`), ale nie powinien być traktowany jako w pełni ukończony kontekst aplikacyjny.
