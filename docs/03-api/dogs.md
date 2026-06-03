# Dogs API

## Register dog

Rejestruje psa jako agregat `Dog`. Powiazanie z karta wlasciciela jest zapisane przez `OwnerCardId`, bez bezposredniego importowania agregatu `OwnerCard`.

```http
POST /api/dogs
Content-Type: application/json
```

```json
{
  "name": "Burek",
  "breed": "LABRADOR",
  "diet": "STANDARD",
  "behavioralProfile": {
    "aggressive": false,
    "sociable": true,
    "trained": true
  },
  "animalTrait": "FRIENDLY",
  "ownerCardId": 3
}
```

Successful response:

```http
HTTP/1.1 201 Created
Location: /api/dogs/1
```

## Get dog

```http
GET /api/dogs/1
```

Zwraca dane psa, diete, profil behawioralny, ceche zwierzecia i `ownerCardId`.

## Change diet

```http
PATCH /api/dogs/1/diet
Content-Type: application/json
```

```json
{
  "diet": "GRAIN_FREE"
}
```

## Change behavioral profile

```http
PATCH /api/dogs/1/behavioral-profile
Content-Type: application/json
```

```json
{
  "aggressive": false,
  "sociable": false,
  "trained": true
}
```
