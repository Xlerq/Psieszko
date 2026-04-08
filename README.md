# Psie przedszkole

System obsługi psiego przedszkola realizowany w ramach przedmiotu **Usługi sieciowe w biznesie**.

Repozytorium zawiera komplet materiałów projektowych:
- dokumentację analityczną,
- dokumentację projektową,
- implementację backendu w Java,
- testy,
- materiały do prezentacji.

## Cel projektu

Celem projektu jest stworzenie aplikacji serwującej usługi sieciowe w architekturze REST dla wybranej dziedziny biznesowej — w tym przypadku **psiego przedszkola**.

## Stack technologiczny

Planowany stos technologiczny:
- Java
- Spring Boot
- Spring Data JPA
- REST API
- PostgreSQL
- Git + GitHub

## Opis katalogów

### `docs/00-organizacja/`
Dokumenty organizacyjne projektu:
- opis projektu,
- zakres projektu,
- role w zespole,
- harmonogram,
- zasady pracy.

### `docs/01-analiza/`
Materiały analityczne:
- wymagania,
- odkrywanie pojęć,
- model dziedziny,
- przypadki użycia,
- diagramy UML analityczne.

### `docs/02-projekt/`
Materiały projektowe:
- architektura systemu,
- podział na moduły,
- model bazy danych,
- decyzje architektoniczne,
- diagramy UML projektowe.

### `docs/03-api/`
Opis API:
- endpointy REST,
- kontrakty,
- scenariusze użycia API,
- przykłady requestów i response’ów.

### `docs/04-testy/`
Materiały testowe:
- plan testów,
- przypadki testowe,
- raporty,
- kolekcje Bruno / inne narzędzia.

### `docs/05-prezentacja/`
Materiały końcowe:
- scenariusz demo,
- checklisty,
- screeny,
- materiały na prezentację.

### `backend/`
Kod aplikacji backendowej w Java / Spring.

### `infra/`
Pliki pomocnicze do uruchamiania projektu lokalnie, np. Docker Compose.

## Zasady pracy

Najważniejsze zasady:
- nie pracujemy bezpośrednio na branchu `main`,
- każda większa zmiana powinna mieć własny branch,
- zmiany trafiają do `main` przez Pull Request,
- commit powinien mieć krótki i sensowny opis,
- dokumentacja jest tak samo ważna jak kod.

Szczegóły znajdują się w pliku [`CONTRIBUTING.md`](./CONTRIBUTING.md).

## Jak zacząć

1. Sklonuj repozytorium.
2. Przeczytaj:
   - `CONTRIBUTING.md`
   - `Instrukcje/*`
3. Utwórz własny branch.
4. Wprowadź zmiany.
5. Wypchnij branch na GitHub.
6. Otwórz Pull Request.

## Autorzy
Do wypełnienia
Projekt realizowany zespołowo w ramach przedmiotu **Usługi sieciowe w biznesie**.
