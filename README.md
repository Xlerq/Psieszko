# Psie Przedszkole

Jesli dopiero zaczynasz prace z tym repo:
- przeczytaj [`ZASADY_WSPÓŁPRACY.md`](./ZASADY_WSPÓŁPRACY.md),
- zajrzyj do [`START_TUTAJ_GIT/README.md`](./START_TUTAJ_GIT/README.md),
- nie pracuj bezposrednio na branchu `main`.

System obslugi psiego przedszkola realizowany w ramach przedmiotu **Uslugi sieciowe w biznesie**.

Repozytorium zawiera komplet materialow projektowych:
- dokumentacje analityczna,
- dokumentacje projektowa,
- implementacje backendu w Java,
- testy,
- materialy do prezentacji.

## Cel projektu

Celem projektu jest stworzenie aplikacji serwujacej uslugi sieciowe w architekturze REST dla wybranej dziedziny biznesowej, w tym przypadku **psiego przedszkola**.

## Stack technologiczny

Planowany stos technologiczny:
- Java
- Spring Boot
- Spring Data JPA
- REST API
- PostgreSQL
- Git + GitHub

## Opis katalogow

### `docs/00-organizacja/`
Dokumenty organizacyjne projektu:
- opis projektu,
- zakres projektu,
- role w zespole,
- harmonogram,
- zasady pracy.

### `docs/01-analiza/`
Materialy analityczne:
- wymagania,
- odkrywanie pojec,
- model dziedziny,
- przypadki uzycia,
- diagramy UML analityczne.

### `docs/02-projekt/`
Materialy projektowe:
- architektura systemu,
- podzial na moduly,
- model bazy danych,
- decyzje architektoniczne,
- diagramy UML projektowe.

### `docs/03-api/`
Opis API:
- endpointy REST,
- kontrakty,
- scenariusze uzycia API,
- przyklady requestow i response'ow.

### `docs/04-testy/`
Materialy testowe:
- plan testow,
- przypadki testowe,
- raporty,
- kolekcje Bruno lub inne narzedzia.

### `docs/05-prezentacja/`
Materialy koncowe:
- scenariusz demo,
- checklisty,
- screeny,
- materialy na prezentacje.

### `START_TUTAJ_GIT/`
Instrukcje dla zespolu:
- pierwsze kroki z Gitem,
- sposob pracy z repo,
- tworzenie pull requestow,
- rozwiazywanie konfliktow.

### `backend/`
Kod aplikacji backendowej w Java i Spring.

### `infra/`
Pliki pomocnicze do uruchamiania projektu lokalnie, na przyklad `docker-compose.yml`.

## Jak zaczac

1. Sklonuj repozytorium.
2. Przeczytaj:
   - [`ZASADY_WSPÓŁPRACY.md`](./ZASADY_WSPÓŁPRACY.md)
   - [`START_TUTAJ_GIT/README.md`](./START_TUTAJ_GIT/README.md)
3. Utworz wlasny branch.
4. Wprowadz zmiany.
5. Wypchnij branch na GitHub.
6. Otworz Pull Request.

## Autorzy

Marek Karbarz (Xler) - Właściciel repozytorium
 
Projekt realizowany zespolowo w ramach przedmiotu **Uslugi sieciowe w biznesie**.
