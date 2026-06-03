# Owner Cards API

## Create owner card

Tworzy kartoteke wlasciciela jako agregat `OwnerCard` z wewnetrzna encja `Owner`.

```http
POST /api/owner-cards
Content-Type: application/json
```

```json
{
  "firstName": "Jan",
  "lastName": "Nowak",
  "phoneNumber": "600700800",
  "email": "jan.nowak@example.com"
}
```

Successful response:

```http
HTTP/1.1 201 Created
Location: /api/owner-cards/1
```

```json
{
  "id": 1,
  "ownerId": 1,
  "firstName": "Jan",
  "lastName": "Nowak",
  "phoneNumber": "600700800",
  "email": "jan.nowak@example.com",
  "dogIds": []
}
```

## Get owner card

```http
GET /api/owner-cards/1
```

Zwraca kartoteke wlasciciela razem z identyfikatorami przypisanych psow.

## Update owner card contact

Aktualizuje dane kontaktowe wlasciciela.

```http
PATCH /api/owner-cards/1
Content-Type: application/json
```

```json
{
  "phoneNumber": "111222333",
  "email": "jan.nowy@example.com"
}
```

## Add dog reference

Dodaje powiazanie kartoteki z psem przez typowany identyfikator `DogId`.

```http
POST /api/owner-cards/1/dogs
Content-Type: application/json
```

```json
{
  "dogId": 12
}
```
