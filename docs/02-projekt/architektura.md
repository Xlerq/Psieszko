# Architektura systemu — Psie Przedszkole

## Styl architektoniczny

System realizowany jest jako aplikacja backendowa w architekturze **REST API**, zbudowana zgodnie z zasadami **Domain-Driven Design (DDD)**.

Warstwa domenowa jest niezależna od frameworka — Spring Boot pełni rolę infrastruktury (HTTP, JPA, konfiguracja), a nie elementu domeny.

---

## Warstwy systemu

```
┌─────────────────────────────────┐
│        REST API (HTTP)          │  ← kontrolery Spring MVC (@RestController)
├─────────────────────────────────┤
│      Warstwa aplikacji          │  ← Application Services (use cases)
├─────────────────────────────────┤
│       Warstwa domenowa          │  ← agregaty, encje, fabryki, repozytoria (interfejsy)
├─────────────────────────────────┤
│     Warstwa infrastruktury      │  ← JPA, Spring Data, PostgreSQL, Docker Compose
└─────────────────────────────────┘
```

### REST API
Kontrolery Spring MVC przyjmują żądania HTTP, mapują je na komendy i delegują do warstwy aplikacji. Zwracają odpowiedzi JSON. Dokumentacja API generowana automatycznie przez SpringDoc OpenAPI (Swagger UI).

### Warstwa aplikacji
Koordynuje przypadki użycia — orkiestruje przepływ między kontrolerami a domeną, obsługuje transakcje (`@Transactional`). Nie zawiera logiki biznesowej. Każdy bounded context ma własny Application Service.

### Warstwa domenowa
Serce systemu — agregaty, encje, fabryki, wyjątki domenowe, interfejsy repozytoriów. Niezależna od Spring Boot i JPA.

### Warstwa infrastruktury
Implementacje repozytoriów przez Spring Data JPA (`JpaRepository`), konfiguracja PostgreSQL, pliki Docker Compose w `infra/`.

---

## Foundation (`edu.prz.psieszko.foundation`)

Foundation to zestaw bazowych typów i abstrakcji współdzielonych przez wszystkie bounded contexty.

| Klasa / Interfejs | Opis |
|-------------------|------|
| `Identity` | Interfejs dla typowanych ID agregatów — kontrakt `Long id()` |
| `BaseEntity` | Baza dla agregatów — `@Id` (auto-generated), `@Version` (optimistic locking) |
| `AuditableEntity` | Automatyczny audyt: `createdAt`, `updatedAt`, `createdBy`, `updatedBy` |
| `StandardFactory<I,T>` | Kontrakt fabryki: `T create(I input)` |
| `DomainException` | Bazowy wyjątek domenowy (RuntimeException) |
| `NotExistsException` | Wyjątek dla nieistniejących zasobów (extends ValidationException) |

Hierarchia klas bazowych:

```
AuditableEntity
    └── BaseEntity   (dodaje id + version)
            └── [każdy agregat / encja]
```

---

## Shared Kernel (`edu.prz.psieszko.shared`)

Shared Kernel zawiera typy współdzielone między bounded contextami — typowane identyfikatory agregatów.

| Klasa | Opis |
|-------|------|
| `DogId` | Typowany ID psa — z walidacją (nie może być null) |
| `EmployeeId` | Typowany ID pracownika |

Wszystkie ID są `@Embeddable` (JPA) i implementują `Identity`.

### Dlaczego typowane ID zamiast bezpośrednich referencji?

Agregaty nie trzymają referencji do obiektów innych agregatów — trzymają tylko ich ID. Przykład: `OwnerCard` przechowuje `Set<DogId>`, nie kolekcję obiektów `Dog`. Dzięki temu:

- agregaty są niezależne i mogą być ładowane osobno,
- granice transakcji są jasno określone (jedna transakcja = jeden agregat),
- niemożliwe jest przypadkowe zmodyfikowanie innego agregatu przez referencję.

---

## Stack technologiczny

| Technologia | Rola |
|-------------|------|
| Java 21 | Język implementacji |
| Spring Boot 4 | Framework aplikacyjny |
| Spring Data JPA | Warstwa dostępu do danych |
| PostgreSQL | Relacyjna baza danych |
| Gradle | Build tool |
| Lombok | Redukcja boilerplate (gettery, konstruktory, equals/hashCode) |
| Docker Compose | Lokalne uruchamianie infrastruktury (`infra/`) |
| SpringDoc OpenAPI | Automatyczna dokumentacja REST API (Swagger UI) |

---

## Uruchamianie lokalne

1. Uruchom bazę danych: `docker compose up -d` (pliki w `infra/`)
2. Uruchom aplikację: `./gradlew bootRun`
3. Swagger UI: `http://localhost:8080/swagger-ui.html`
4. Testy: `./gradlew test`
