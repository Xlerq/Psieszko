# Reservations API

## Create reservation

Tworzy rezerwacje dla psa. Powiazanie z psem jest zapisane przez `DogId`.

```http
POST /api/reservations
Content-Type: application/json
```

```json
{
  "dogId": 7,
  "startDate": "2030-07-01T08:00:00",
  "endDate": "2030-07-05T16:00:00",
  "paymentAmount": 250.00
}
```

Successful response:

```http
HTTP/1.1 201 Created
Location: /api/reservations/1
```

## Get reservation

```http
GET /api/reservations/1
```

Zwraca rezerwacje z terminem, statusem i platnoscia.

## Change reservation date

```http
PATCH /api/reservations/1/date
Content-Type: application/json
```

```json
{
  "startDate": "2030-08-01T08:00:00",
  "endDate": "2030-08-05T16:00:00"
}
```

## Cancel reservation

```http
POST /api/reservations/1/cancel
```

Zmienia status rezerwacji na `CANCELLED`.

## Mark payment as paid

```http
POST /api/reservations/1/payment
```

Zmienia status platnosci na `PAID`.
