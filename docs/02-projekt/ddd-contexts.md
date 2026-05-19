# Konteksty DDD i agregaty — Psie Przedszkole

## Mapa bounded contextów

System podzielony jest na siedem bounded contextów:

```
┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│     ownercard        │   │        dogs          │   │       service        │
│                      │   │                      │   │                      │
│  ► OwnerCard (pełny) │   │  ► Dog (pełny)       │   │  ► Reservation       │
│    Owner             │   │                      │   │                      │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘

┌──────────────────────┐   ┌──────────────────────┐   ┌──────────────────────┐
│  kindergartenstructure│  │     dailyjournal     │   │        health        │
│                      │   │                      │   │                      │
│  ► Kindergarten(pełny)│  │  ► DailyJournal      │   │  ► HealthCard        │
│    Employee, Role    │   │                      │   │                      │
└──────────────────────┘   └──────────────────────┘   └──────────────────────┘

                        ┌──────────────────────┐
                        │        lesson        │
                        │                      │
                        │  ► Lesson            │
                        └──────────────────────┘

                  ════════════════════════════════
                            Shared Kernel
                     (DogId, EmployeeId, Identity)
                  ════════════════════════════════
                            Foundation
               (BaseEntity, AuditableEntity, StandardFactory,
                    DomainException, NotExistsException)
```

---

## Foundation

Pakiet: `edu.prz.psieszko.foundation`

Wspólna baza techniczna dla wszystkich kontekstów. Szczegóły w `docs/02-projekt/architektura.md`.

---

## Shared Kernel

Pakiet: `edu.prz.psieszko.shared`

Typowane identyfikatory współdzielone między kontekstami: `DogId`, `EmployeeId`.

Agregaty nie przechowują referencji do obiektów z innych kontekstów — używają wyłącznie typowanych ID z Shared Kernel.

---

## Agregaty

### OwnerCard — Karta właściciela

**Pakiet:** `edu.prz.psieszko.ownercard`  
**Status: pełny** — zaimplementowany agregat, fabryka, repozytorium, serwis aplikacyjny, kontroler REST

**Aggregate root:** `OwnerCard`  
**Encje wewnętrzne:** `Owner`

**Pola OwnerCard:**
- `owner` — encja `Owner` (relacja `@OneToOne`, kaskada ALL)
- `dogIds` — zbiór `Set<DogId>` (psy właściciela, powiązanie przez ID)

**Pola Owner:**
- `firstName`, `lastName` — imię i nazwisko (wymagane)
- `phoneNumber`, `email` — dane kontaktowe (wymagane, z walidacją)

**Zachowania:**
- `OwnerCard(Owner)` — konstruktor pakietowy, tworzony przez fabrykę
- `addDog(DogId)` — dodanie psa do karty właściciela
- `getDogIds()` — zwraca niemutowalny widok zbioru psów
- `Owner.updateContact(phoneNumber, email)` — aktualizacja danych kontaktowych

**Fabryka:** `OwnerCardFactory` — przyjmuje `Input(firstName, lastName, phoneNumber, email)`, tworzy `OwnerCard` z `Owner`

**REST API:** `POST /api/owner-cards` — tworzy nową kartę właściciela, zwraca `201 Created`

**Tabele bazy danych:** `owner_cards`, `owners`, `owner_card_dogs`

---

### Dog — Pies

**Pakiet:** `edu.prz.psieszko.dogs`  
**Status: pełny** — zaimplementowany agregat, fabryka, repozytorium, serwis aplikacyjny

**Aggregate root:** `Dog`

**Pola Dog:**
- `id` — identyfikator (Long)
- `name` — imię psa

**Fabryka:** `DogFactory` (interfejs — do implementacji)  
**Repozytorium:** `DogRepository extends JpaRepository<Dog, Long>`

---

### Kindergarten — Struktura przedszkola

**Pakiet:** `edu.prz.psieszko.kindergartenstructure`  
**Status: pełny** — zaimplementowany agregat, fabryka (konkretna), repozytorium, serwis aplikacyjny

**Aggregate root:** `Kindergarten`  
**Encje wewnętrzne:** `Employee`, `Role`

**Pola Kindergarten:**
- `name` — nazwa przedszkola
- `employees` — lista pracowników (`@OneToMany`)
- `roles` — lista ról (`@OneToMany`)

**Pola Employee:**
- `firstName`, `lastName` — imię i nazwisko pracownika

**Pola Role:**
- `name` — nazwa roli

**Fabryka:** `KindergartenFactory` — tworzy `Kindergarten` z nazwy (String)  
**Repozytorium:** `KindergartenRepository extends JpaRepository<Kindergarten, Long>`

> Uwaga: `Kindergarten` ma więcej niż jedną encję wewnętrzną (`Employee` i `Role`).

---

### Reservation — Rezerwacja

**Pakiet:** `edu.prz.psieszko.service`  
**Status: szkielet** — zdefiniowany agregat, fabryka i repozytorium (interfejsy), serwis aplikacyjny

**Aggregate root:** `Reservation`

**Planowane powiązania:**
- `Dog` (przez `DogId`)
- `Lesson`
- `Payment`
- `VeterinaryVisit`

---

### DailyJournal — Dziennik dnia

**Pakiet:** `edu.prz.psieszko.dailyjournal`  
**Status: szkielet** — zdefiniowany agregat, fabryka i repozytorium (interfejsy), serwis aplikacyjny

**Aggregate root:** `DailyJournal`

**Planowane powiązania:**
- `Dog` (przez `DogId`)
- `Activity`
- `Incident`
- `Meal`

---

### HealthCard — Karta zdrowia

**Pakiet:** `edu.prz.psieszko.health`  
**Status: szkielet** — zdefiniowany agregat, fabryka i repozytorium (interfejsy), serwis aplikacyjny

**Aggregate root:** `HealthCard`

**Planowane powiązania:**
- `Dog` (przez `DogId`)
- `Veterinarian`
- `VeterinaryVisit`
- `Vaccination`
- `Medicine`

---

### Lesson — Zajęcia

**Pakiet:** `edu.prz.psieszko.lesson`  
**Status: szkielet** — zdefiniowany agregat, fabryka i repozytorium (interfejsy), serwis aplikacyjny

**Aggregate root:** `Lesson`

**Planowane powiązania:**
- `Employee` (przez `EmployeeId`)
- `Topic`
- `LearningZone`
- `TrainingEquipment`

---

## Podsumowanie — które agregaty są pełne

| Agregat | Kontekst | Pełny? | Więcej niż jedna encja? |
|---------|----------|--------|------------------------|
| `OwnerCard` | ownercard | tak | tak (`Owner`) |
| `Dog` | dogs | tak | nie |
| `Kindergarten` | kindergartenstructure | tak | tak (`Employee`, `Role`) |
| `Reservation` | service | nie (szkielet) | — |
| `DailyJournal` | dailyjournal | nie (szkielet) | — |
| `HealthCard` | health | nie (szkielet) | — |
| `Lesson` | lesson | nie (szkielet) | — |
