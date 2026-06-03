# API kontekstu Lekcja

Endpointy sluza do prowadzenia lekcji z przypisanym pracownikiem, tematem, strefa nauki i wyposazeniem.

## Utworzenie lekcji

```http
POST /api/lessons
```

```json
{
  "employeeId": 7,
  "topic": {
    "name": "Podstawowe komendy",
    "description": "Siad i zostan"
  },
  "learningZone": {
    "name": "Sala A",
    "location": "Parter"
  },
  "equipment": [
    { "name": "Kliker" },
    { "name": "Mata" }
  ],
  "startDate": "2030-07-01T10:00:00",
  "endDate": "2030-07-01T11:00:00"
}
```

## Pobranie lekcji

```http
GET /api/lessons/{id}
```

## Zarzadzanie lekcja

```http
PATCH /api/lessons/{id}/topic
PATCH /api/lessons/{id}/learning-zone
PATCH /api/lessons/{id}/employee
PATCH /api/lessons/{id}/equipment
```

API waliduje wymagane pola requestu. Lekcja musi miec date rozpoczecia wczesniejsza niz date zakonczenia.
