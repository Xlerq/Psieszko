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
  "email": "jan.nowak@example.com"
}
```
