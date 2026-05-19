# Architektura systemu — Psie Przedszkole

## Styl architektoniczny

System jest aplikacją backendową udostępniającą usługi w architekturze **REST API**. Kod jest zorganizowany zgodnie z podejściem **Domain-Driven Design (DDD)**: projekt dzielony jest na bounded contexty, a w każdym kontekście wydzielone są pakiety `application` oraz `domain`.

Aktualna implementacja łączy model domenowy z mechanizmem utrwalania danych przez JPA. Oznacza to, że klasy domenowe znajdują się w pakietach `domain`, ale jednocześnie zawierają adnotacje takie jak `@Entity`, `@Embeddable`, `@Embedded`, `@OneToOne` lub `@Enumerated`. Dlatego domena nie jest całkowicie niezależna od JPA — JPA jest obecnie używane bezpośrednio na klasach domenowych.

---

## Warstwy systemu

```text
┌─────────────────────────────────┐
│        REST API (HTTP)          │  kontrolery REST, request/response JSON
├─────────────────────────────────┤
│      Warstwa aplikacji          │  Application Services, przypadki użycia
├─────────────────────────────────┤
│       Warstwa domenowa          │  agregaty, encje, value objects, fabryki
├─────────────────────────────────┤
│     Warstwa persystencji        │  Spring Data JPA, repozytoria, H2
└─────────────────────────────────┘
```

### REST API

Warstwa REST odpowiada za obsługę żądań HTTP, mapowanie danych wejściowych na komendy lub obiekty wejściowe oraz zwracanie odpowiedzi JSON. Dokumentacja API jest udostępniana przez SpringDoc OpenAPI / Swagger UI.

### Warstwa aplikacji

Warstwa aplikacji zawiera serwisy aplikacyjne odpowiedzialne za realizację przypadków użycia. Serwisy aplikacyjne koordynują pracę domeny i repozytoriów, ale nie powinny zawierać głównej logiki biznesowej agregatów.

### Warstwa domenowa

Warstwa domenowa zawiera najważniejsze pojęcia biznesowe systemu: agregaty, encje wewnętrzne, value objects, fabryki oraz interfejsy repozytoriów. W aktualnym kodzie klasy domenowe są jednocześnie mapowane przez JPA, więc znajdują się na nich adnotacje persystencji.

### Warstwa persystencji

Dane są utrwalane z użyciem Spring Data JPA. Repozytoria rozszerzają `JpaRepository`, a konfiguracja bazy znajduje się w `src/main/resources/application.properties`.

---

## Foundation (`edu.prz.psieszko.foundation.domain`)

Foundation to wspólny zestaw klas i interfejsów wykorzystywany w różnych bounded contextach. Nie opisuje konkretnego obszaru biznesowego, tylko dostarcza bazowe mechanizmy techniczne i domenowe.

| Klasa / interfejs | Rola w projekcie |
| --- | --- |
| `Identity` | Wspólny interfejs dla typowanych identyfikatorów. Definiuje metodę `Long id()`. |
| `AuditableEntity` | Bazowa klasa z polami audytowymi, np. datą utworzenia i aktualizacji. |
| `BaseEntity` | Bazowa klasa encji/agregatów. Zawiera `id`, `version`, `equals()` i `hashCode()`. |
| `StandardFactory<I, T>` | Generyczny kontrakt fabryki tworzącej obiekt domenowy na podstawie danych wejściowych. |
| `DomainException` | Bazowy wyjątek domenowy. |
| `NotExistsException` | Wyjątek używany dla sytuacji, gdy wymagany zasób nie istnieje. |

Hierarchia bazowych encji:

```text
AuditableEntity
    └── BaseEntity
            └── konkretne agregaty i encje domenowe
```

W obecnym kodzie po `BaseEntity` dziedziczą m.in. `Dog`, `OwnerCard`, `Owner`, `Reservation`, `Kindergarten`, `Employee` i `Role`. `HealthCard`, `Lesson` i `DailyJournal` są obecnie szkicami agregatów i nie dziedziczą jeszcze po `BaseEntity`.

---

## Shared Kernel (`edu.prz.psieszko.shared.identity`)

Shared Kernel zawiera elementy współdzielone między bounded contextami. W projekcie są to przede wszystkim typowane identyfikatory używane do wskazywania agregatów z innych kontekstów.

| Klasa | Opis |
| --- | --- |
| `DogId` | Typowany identyfikator psa. |
| `EmployeeId` | Typowany identyfikator pracownika. |
| `HealthCardId` | Typowany identyfikator karty zdrowia. |
| `LessonId` | Typowany identyfikator zajęć. |
| `OwnerCardId` | Typowany identyfikator karty właściciela. |
| `ReservationId` | Typowany identyfikator rezerwacji. |

Identyfikatory są typami osadzanymi przez JPA (`@Embeddable`) i implementują wspólny interfejs `Identity`.

### Po co jest Shared Kernel?

Shared Kernel pozwala współdzielić niewielką część modelu między bounded contextami bez tworzenia silnych zależności między całymi agregatami. Dzięki temu konteksty mogą odwoływać się do siebie przez identyfikatory, a nie przez bezpośrednie referencje obiektowe.

Przykład: agregat `Dog` przechowuje `OwnerCardId`, a nie cały obiekt `OwnerCard`. Agregat `Reservation` przechowuje `DogId`, a nie cały obiekt `Dog`.

### Dlaczego typowane ID zamiast bezpośrednich zależności między agregatami?

Typowane identyfikatory ograniczają przypadkowe mieszanie identyfikatorów różnych agregatów. `DogId`, `OwnerCardId` i `ReservationId` są osobnymi typami, mimo że technicznie przechowują wartość liczbową. Dzięki temu kod jest czytelniejszy i łatwiej zauważyć błędne powiązania.

Takie podejście wspiera również granice agregatów: jeden agregat nie modyfikuje bezpośrednio wnętrza innego agregatu przez referencję obiektową. Jeśli potrzebne są dane z innego agregatu, powinny być pobrane przez odpowiednie repozytorium lub serwis aplikacyjny.

---

## Aktualny stack technologiczny

| Technologia | Rola |
| --- | --- |
| Java 21 | Język implementacji. |
| Spring Boot 4 | Główny framework aplikacyjny. |
| Spring Web MVC | Obsługa REST API. |
| Spring Data JPA | Dostęp do danych i repozytoria. |
| H2 | Aktualnie skonfigurowana baza danych (`jdbc:h2:file:./data/psieszko`). |
| Gradle | Narzędzie budowania projektu. |
| Lombok | Redukcja boilerplate w klasach Java. |
| SpringDoc OpenAPI | Swagger UI i dokumentacja API. |

W katalogu `infra/` znajduje się obecnie tylko plik `.gitkeep`, dlatego dokumentacja nie zakłada istnienia działającego `docker-compose.yml`.

---

## Uruchamianie lokalne

1. Uruchom aplikację:

```bash
./gradlew bootRun
```

Na Windowsie można użyć:

```powershell
.\gradlew.bat bootRun
```

2. Aplikacja domyślnie działa na porcie `8080`.

3. Konsola H2 jest włączona w konfiguracji aplikacji.

4. Dane H2 zapisywane są lokalnie w ścieżce:

```text
./data/psieszko
```

5. Testy można uruchomić poleceniem:

```bash
./gradlew test
```
