# API kontekstu Zdrowie

Endpointy sluza do prowadzenia historii medycznej psa wskazanego przez `DogId`.

## Utworzenie karty zdrowia

```http
POST /api/health-cards
```

```json
{
  "dogId": 12
}
```

## Pobranie karty zdrowia

```http
GET /api/health-cards/{id}
GET /api/health-cards/dog/{dogId}
```

## Rejestracja wizyty weterynaryjnej

```http
POST /api/health-cards/{id}/veterinary-visits
```

```json
{
  "veterinarian": {
    "name": "Anna Wet",
    "licenseNumber": "PW-123"
  },
  "visitDate": "2030-01-10",
  "description": "Kontrola po szczepieniu",
  "recommendations": "Obserwowac apetyt"
}
```

## Aktualizacja szczepien i lekow

```http
PATCH /api/health-cards/{id}/vaccinations
PATCH /api/health-cards/{id}/medicines
```

API waliduje wymagane pola requestu. Data kolejnego szczepienia nie moze byc wczesniejsza od daty szczepienia, a data konca przyjmowania leku nie moze byc wczesniejsza od daty rozpoczecia.
