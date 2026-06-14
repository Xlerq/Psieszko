# Psie Przedszkole

Backend REST API dla psiego przedszkola, przygotowany jako zespołowy projekt akademicki z przedmiotu **Usługi sieciowe w biznesie**.

Projekt modeluje procesy związane z obsługą właścicieli, psów, rezerwacji, lekcji szkoleniowych, kart zdrowia, dzienników dnia oraz struktury pracowników przedszkola. Kod został zorganizowany w stylu inspirowanym **Domain-Driven Design**, z wyraźnym podziałem na bounded contexty, warstwę aplikacyjną i warstwę domenową.

## Status projektu

Projekt jest ukończony w zakresie wymaganym na potrzeby przedmiotu i został zarchiwizowany. Repozytorium pozostaje dostępne jako przykład pracy zespołowej, dokumentacji projektowej oraz implementacji backendu w Java/Spring Boot.

> Uwaga: repozytorium powstało jako projekt akademicki.

## Najważniejsze elementy

- REST API dla zarządzania psim przedszkolem.
- Model domenowy podzielony na bounded contexty.
- Typowane identyfikatory agregatów w shared kernel.
- Warstwa aplikacyjna z serwisami przypadków użycia.
- Kontrolery REST z walidacją danych wejściowych.
- Utrwalanie danych przez Spring Data JPA i H2.
- Dokumentacja analityczna, projektowa i API.
- Testy jednostkowe oraz testy warstwy aplikacyjnej/kontrolerów.

## Stack technologiczny

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Gradle
- Lombok
- SpringDoc OpenAPI / Swagger UI
- JUnit

## Uruchamianie lokalne

Wymagania:

- JDK 21
- Git

Uruchomienie aplikacji:

```bash
./gradlew bootRun
```

Na Windowsie:

```powershell
.\gradlew.bat bootRun
```

## Testy

Uruchomienie testów:

```bash
./gradlew test
```

Testy są również uruchamiane automatycznie w GitHub Actions po zmianach w repozytorium.

## Adresy lokalne

Po uruchomieniu aplikacji na domyślnym porcie `8080` dostępne są:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- H2 Console: http://localhost:8080/h2-console

Konfiguracja bazy H2 jest zgodna z `src/main/resources/application.properties`:

```text
JDBC URL: jdbc:h2:file:./data/psieszko
Username: sa
Password:
```

## Dokumentacja

Repozytorium zawiera komplet materiałów projektowych:

- [`docs/00-organizacja/`](./docs/00-organizacja/) - organizacja pracy zespołu,
- [`docs/01-analiza/`](./docs/01-analiza/) - wymagania, słownik pojęć i model dziedziny,
- [`docs/02-projekt/`](./docs/02-projekt/) - architektura i konteksty DDD,
- [`docs/03-api/`](./docs/03-api/) - opis endpointów REST i przykłady użycia.

Najważniejsze dokumenty:

- [Architektura systemu](./docs/02-projekt/architektura.md)
- [Konteksty DDD i agregaty](./docs/02-projekt/ddd-contexts.md)
- [Wymagania biznesowe](./docs/01-analiza/wymagania.md)

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
- przykłady requestów i response'ów.

### `docs/04-testy/`
Katalog zarezerwowany na dodatkowe materiały testowe. Aktualne testy automatyczne znajdują się w `src/test/java`.

### `docs/05-prezentacja/`
Katalog zarezerwowany na materiały prezentacyjne, jeśli będą potrzebne poza dokumentacją w `docs/`.

### `START_TUTAJ_GIT/`
Instrukcje dla zespołu:
- pierwsze kroki z Gitem,
- sposób pracy z repo,
- tworzenie pull requestów,
- rozwiązywanie konfliktów.

### `src/`
Kod aplikacji backendowej w Java i Spring.

### `infra/`
Miejsce na pliki pomocnicze do uruchamiania projektu lokalnie.

## Praca z repozytorium

Jeśli dopiero zaczynasz pracę z tym repozytorium:

1. Sklonuj repozytorium.
2. Przeczytaj:
   - [`ZASADY_WSPÓŁPRACY.md`](./ZASADY_WSPÓŁPRACY.md)
   - [`START_TUTAJ_GIT/README.md`](./START_TUTAJ_GIT/README.md)
3. Utwórz własny branch.
4. Wprowadź zmiany.
5. Wypchnij branch na GitHub.
6. Otwórz Pull Request.

## Autorzy
[`role-i-odpowiedzialnosci.md`](docs/00-organizacja/role-i-odpowiedzialnosci.md)


Projekt realizowany zespołowo w ramach przedmiotu **Usługi sieciowe w biznesie**.
