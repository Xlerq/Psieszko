# API struktury przedszkola

Endpointy kontekstu struktury przedszkola pozwalają zarządzać pracownikami oraz przypisywać im role.

## Utworzenie pracownika

`POST /api/kindergarten/employees`

Body:

```json
{
  "firstName": "Anna",
  "lastName": "Kowalska",
  "phoneNumber": "600700800",
  "email": "anna.kowalska@example.com"
}
```

Odpowiedź `201 Created` zwraca dane utworzonego pracownika.

## Pobranie pracownika

`GET /api/kindergarten/employees/{employeeId}`

Odpowiedź zawiera dane pracownika oraz przypisane role.

## Lista pracowników

`GET /api/kindergarten/employees`

Odpowiedź zawiera listę pracowników posortowaną po nazwisku, imieniu i identyfikatorze.

## Przypisanie roli

`POST /api/kindergarten/employees/{employeeId}/roles`

Body:

```json
{
  "roleName": "Teacher"
}
```

Jeżeli rola o podanej nazwie jeszcze nie istnieje, zostanie utworzona i przypisana do pracownika.
