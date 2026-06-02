# Role i odpowiedzialności

Dokument porządkuje role projektowe oraz dotychczasowy podział odpowiedzialności w zespole realizującym projekt `Psieszko`.

## Role w projekcie

### Analityk

Analityk odpowiada za zrozumienie dziedziny psiego przedszkola i opisanie jej w dokumentacji. Dba o to, żeby wymagania, słownik pojęć, model dziedziny oraz przypadki użycia były spójne z zakresem projektu i możliwe do zaimplementowania przez developerów.

Produkty pracy analityka:

- wymagania biznesowe i funkcjonalne,
- słownik pojęć domenowych,
- odkrywanie encji i agregatów,
- model dziedziny,
- opis aktorów i przypadków użycia,
- odpowiedzialności przypadków użycia.

### Developer

Developer odpowiada za implementację backendu w Java/Spring Boot zgodnie z przyjętą architekturą DDD. Dba o encje, agregaty, repozytoria, fabryki, use case'y, kontrolery REST, walidację oraz integrację ze Swagger/OpenAPI i bazą H2.

Produkty pracy developera:

- kod aplikacji w `src/main/java`,
- encje i agregaty domenowe,
- serwisy aplikacyjne i przypadki użycia,
- kontrolery REST,
- repozytoria JPA,
- konfiguracja techniczna projektu,
- dokumentacja API dla zaimplementowanych endpointów.

### Tester

Tester odpowiada za sprawdzenie, czy zaimplementowane funkcje działają zgodnie z wymaganiami oraz czy projekt pozostaje stabilny po zmianach. Testowanie obejmuje testy automatyczne, testy endpointów REST i weryfikację scenariuszy demonstracyjnych.

Produkty pracy testera:

- testy automatyczne w `src/test/java`,
- testy kontekstu Spring Boot,
- testy serwisów aplikacyjnych,
- testy kontrolerów REST,
- przypadki testowe HTTP, np. Bruno/OpenCollection, jeśli zostaną dodane,
- raport lub notatki z wykonania testów.

### Osoba od prezentacji

Osoba od prezentacji odpowiada za przygotowanie sposobu pokazania projektu prowadzącemu. Zbiera najważniejsze funkcje, ustala scenariusz demo, pilnuje materiałów w `docs/05-prezentacja` i koordynuje próbę prezentacji.

Produkty pracy osoby od prezentacji:

- scenariusz prezentacji,
- checklista demo,
- lista endpointów i funkcji do pokazania,
- zrzuty ekranu lub materiały pomocnicze,
- ustalenie kolejności wypowiedzi członków zespołu.

## Punkt kontrolny prezentacji

Data prezentacji nie została jeszcze potwierdzona w repozytorium. Po ustaleniu terminu przez zespół albo prowadzącego należy dopisać ją tutaj jako punkt kontrolny:

| Punkt kontrolny | Data | Odpowiedzialny | Status |
| --- | --- | --- | --- |
| Prezentacja projektu | do ustalenia | osoba od prezentacji / zespół | oczekuje na potwierdzenie |

## Odpowiedzialności osób w zespole

Podział poniżej wynika z dotychczasowych zmian w repozytorium i powinien być aktualizowany po kolejnych PR-ach.

| Osoba | Rola / obszar | Odpowiedzialność |
| --- | --- | --- |
| Marek Karbarz / Xler | właściciel repozytorium, koordynacja | organizacja repozytorium, scalanie PR-ów, porządkowanie dokumentacji, koordynacja pracy zespołu |
| Karolina Kisała | analityk | wymagania, odkrywanie pojęć, model dziedziny i dokumentacja analityczna |
| Szymon Hawryluk | developer | kontekst psa, agregat `Dog`, kontekst rezerwacji, agregat `Reservation`, testy aplikacyjne |
| Maciej Nowak | developer | kontekst zdrowia, kontekst lekcji, poprawki importów i testów |
| Bartek Kogut | developer / dokumentacja projektowa | architektura, konteksty DDD, doprecyzowanie dziedziczenia po `BaseEntity` |
| Bartek Kędziora | developer | kontekst dziennika dnia i struktury przedszkola |
| Małgorzata Jagieła | developer | początkowe klasy `foundation domain`, kontekst struktury przedszkola / pracowników |
| Krzysztof Koza | developer / tester techniczny | warstwa foundation API, globalna obsługa błędów i testy obsługi wyjątków |
| Huttman15 | developer | agregat karty właściciela i poprawki w kontekście `ownercard` |
| Maciej Koryl | developer | startowa wersja aplikacji |
| olakopi | developer | typowane identyfikatory shared kernel i uporządkowanie foundation domain |

## Zasady utrzymywania podziału prac

- Każdy większy obszar funkcjonalny powinien mieć wskazaną osobę odpowiedzialną.
- Po każdym PR-ze należy dopisać albo zaktualizować odpowiedzialność, jeśli zmienił się zakres prac.
- Jeśli jedna osoba przejmuje zadanie po innej osobie, w tabeli należy zostawić informację o dotychczasowym i aktualnym właścicielu obszaru.
- Termin prezentacji należy dopisać od razu po potwierdzeniu daty przez prowadzącego albo zespół.
