# Plan tworzenia projektu Psieszko

## Punkt wyjścia

Projekt ma być aplikacją backendową w Java/Spring Boot serwującą usługi REST dla domeny psiego przedszkola.

Najważniejsze wymagania z materiałów źródłowych:

- produkt końcowy to działające i udokumentowane oprogramowanie,
- aplikacja ma serwować usługi REST,
- stos technologiczny: Java, Spring, JPA, H2, REST, Swagger/OpenAPI,
- kod ma być pisany w stylu DDD,
- wszystkie artefakty mają być w repozytorium Git,
- wymagany jest opis projektu w Markdown lub Word,
- część analityczna ma zawierać wymagania, odkrywanie encji, analityczny model dziedziny, przypadki użycia, aktorów i odpowiedzialności przypadków użycia,
- część testowa ma zawierać kolekcję testów HTTP w Bruno/OpenCollection dla zaimplementowanych usług REST oraz bazę H2 po wykonaniu testów,
- model dziedziny powinien mieć co najmniej 20 encji,
- trzeba mieć co najmniej 3 agregaty, w tym jeden agregat złożony z więcej niż jednej encji,
- trzeba opisać i zaimplementować co najmniej 10 przypadków użycia.

## Wnioski z repozytorium przykładowego

Z `Tech-Bank/car-service` warto skopiować styl pracy, nie samą domenę:

- osobny bounded context w pakiecie, np. `maintenance`,
- wewnątrz kontekstu podział na `domain` i `application`,
- encje JPA w domenie,
- repozytoria jako interfejsy rozszerzające `JpaRepository`,
- fabryka dla agregatu,
- osobna klasa use case dla ważnej operacji biznesowej,
- kontroler REST per zasób,
- walidacja requestów przez `jakarta.validation`,
- wspólna obsługa błędów i standardowa odpowiedź problemowa,
- Swagger/OpenAPI jako dokumentacja kontraktów,
- H2 jako baza lokalna,
- kolekcja testów HTTP w katalogu `testcase`.

Aktualne repo `Psieszko` ma już sensowny zarys pakietów DDD i mapę kontekstów w `docs/02-projekt/ddd-contexts.md`, ale implementacja jest głównie szkieletem. Brakuje pełnych encji, use case'ów, kontrolerów REST, testów Bruno, testów aplikacyjnych i pełnej dokumentacji projektowej.

## Docelowy zakres domeny

Docelowo projekt powinien trzymać się kontekstów widocznych w `docs/01-analiza` i `docs/02-projekt/ddd-contexts.md`.

| Kontekst | Pakiet | Agregat główny | Encje i pojęcia |
| --- | --- | --- | --- |
| Karta Właściciela | `ownercard` | `OwnerCard` | `OwnerCard`, `Owner` |
| Dane Psa | `dogs` | `Dog` | `Dog`, `Breed`, `Diet`, `BehavioralProfile`, `AnimalTrait` |
| Zdrowie | `health` | `HealthCard` | `HealthCard`, `Veterinarian`, `VeterinaryVisit`, `Vaccination`, `Medicine` |
| Usługa | `service` | `Reservation` | `Reservation`, `Payment` |
| Lekcja | `lesson` | `Lesson` | `Lesson`, `Topic`, `LearningZone`, `TrainingEquipment` |
| Dziennik Dnia | `dailyjournal` | `DailyJournal` | `DailyJournal`, `Activity`, `Incident`, `Meal` |
| Struktura Przedszkola | `kindergartenstructure` | `Kindergarten` | `Kindergarten`, `Employee`, `Role` |

Taki model daje 24 encje, więc spełnia wymaganie minimum 20 encji. Ma też 7 agregatów, w tym kilka agregatów złożonych z wielu encji.

## Minimalny zestaw przypadków użycia

Do zaliczenia wystarczy 10, ale plan zakłada pełniejszy zestaw, żeby zespół miał bufor i żeby dokumentacja była spójna z diagramami.

MVP wymagane do implementacji:

1. Zarządzanie pracownikami i rolami.
2. Zarządzanie kartoteką właściciela.
3. Rejestracja danych psa.
4. Ustalenie diety psa.
5. Tworzenie profilu behawioralnego.
6. Wprowadzenie rasy i cech psa.
7. Wprowadzenie rezerwacji pobytu.
8. Obsługa płatności po pobycie.
9. Rejestracja przeprowadzonych lekcji.
10. Wpisywanie aktywności do dziennika dnia.
11. Rejestracja incydentów.
12. Rejestracja wizyt weterynaryjnych.
13. Aktualizacja rejestru szczepień.
14. Wprowadzanie przyjmowanych leków.

Przypadki podglądowe mogą być realizowane przez endpointy `GET`:

- przeglądanie własnej kartoteki,
- monitorowanie aktywności psa w dzienniku,
- podgląd historii medycznej,
- podgląd statusu płatności.

## Architektura kodu

W każdym kontekście stosujemy ten sam układ:

```text
src/main/java/edu/prz/psieszko/{context}/
  application/
    {Action}{Aggregate}UseCase.java
    {Aggregate}Controller.java
  domain/
    {Aggregate}.java
    {ChildEntity}.java
    {Aggregate}Factory.java
    {Aggregate}Repository.java
```

Zasady implementacji:

- encje JPA dziedziczą po `BaseEntity`, jeśli mają własny cykl życia,
- identyfikatory obcych agregatów zapisujemy jako proste pola lub małe value objecty w `shared/identity`,
- logika zmiany stanu jest metodą agregatu, a nie kodem rozrzuconym w kontrolerze,
- kontroler tylko przyjmuje request, odpala use case i zwraca response,
- każda operacja zmieniająca stan ma osobny use case z `@Transactional`,
- repository służy do zapisu i odczytu agregatu,
- factory tworzy poprawny stan początkowy agregatu,
- requesty mają walidację `jakarta.validation`,
- błędy domenowe idą przez wspólny `GlobalExceptionHandler`,
- endpointy są tagowane w Swaggerze.

Proponowany format endpointów:

```text
/api/kindergarten/employees
/api/owner-cards
/api/dogs
/api/dogs/{id}/diet
/api/dogs/{id}/behavioral-profile
/api/health-cards
/api/health-cards/{id}/vaccinations
/api/reservations
/api/reservations/{id}/payment
/api/lessons
/api/daily-journals
/api/daily-journals/{id}/activities
/api/daily-journals/{id}/incidents
```

## Dokumentacja docelowa

Obecna dokumentacja wymaga rozszerzenia. Docelowo repo powinno mieć:

```text
docs/00-organizacja/
  opis-projektu.md
  role-i-odpowiedzialnosci.md
  plan-projektu.md

docs/01-analiza/
  wymagania.md
  slownik-pojec.md
  odkrywanie-encji.md
  model-dziedziny.md
  przypadki-uzycia.md
  odpowiedzialnosci-przypadkow-uzycia.md

docs/02-projekt/
  architektura.md
  ddd-contexts.md
  model-bazy-danych.md
  decyzje-architektoniczne.md

docs/03-api/
  openapi.md
  kontrakty-rest.md
  przyklady-request-response.md

docs/04-testy/
  plan-testow.md
  przypadki-testowe.md
  raport-testow.md

docs/05-prezentacja/
  scenariusz-demo.md
  checklista-prezentacji.md
```

## Definition of Done

Zadanie jest zakończone dopiero wtedy, gdy:

- kod się kompiluje,
- testy przechodzą,
- endpoint jest widoczny w Swaggerze, jeśli dotyczy API,
- requesty mają walidację,
- błędy są obsługiwane przez wspólny mechanizm,
- dokumentacja została zaktualizowana,
- kolekcja Bruno została zaktualizowana dla endpointów REST,
- Pull Request nie zawiera przypadkowych zmian spoza zakresu zadania.
