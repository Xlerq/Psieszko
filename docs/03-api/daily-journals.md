# API dziennika dnia psa

Endpointy dziennika dnia pozwalają prowadzić zapis pobytu psa w placówce. Pies jest wskazywany przez `DogId` w ścieżce URL.

## Utworzenie dziennika dnia

`POST /api/dogs/{dogId}/daily-journals`

Body:

```json
{
  "journalDate": "2026-06-02"
}
```

Odpowiedź `201 Created` zwraca dziennik dnia z listami wpisów.

## Wpis aktywności

`POST /api/dogs/{dogId}/daily-journals/{journalDate}/activities`

Body:

```json
{
  "occurredAt": "09:15:00",
  "description": "Morning walk and social play"
}
```

## Rejestracja incydentu

`POST /api/dogs/{dogId}/daily-journals/{journalDate}/incidents`

Body:

```json
{
  "occurredAt": "11:30:00",
  "description": "Dog became nervous during group play",
  "actionTaken": "Moved dog to a calmer zone"
}
```

## Wpis posiłku

`POST /api/dogs/{dogId}/daily-journals/{journalDate}/meals`

Body:

```json
{
  "servedAt": "13:00:00",
  "foodName": "Dry food",
  "amountInGrams": 180,
  "notes": "Ate full portion"
}
```

## Podgląd dziennika psa

`GET /api/dogs/{dogId}/daily-journals/{journalDate}`

Odpowiedź zawiera identyfikator dziennika, `DogId`, datę, aktywności, incydenty i posiłki z danego dnia.
