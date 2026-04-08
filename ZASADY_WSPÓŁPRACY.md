# Zasady współpracy w repozytorium

Ten plik opisuje zasady pracy z repozytorium projektu **psie_przedszkole**.

## 1. Język projektu

W projekcie używamy **języka polskiego** w:
- dokumentacji,
- nazwach zadań,
- opisach Pull Requestów,
- opisach issue,
- komentarzach organizacyjnych.

Kod może zawierać nazewnictwo techniczne po angielsku, jeśli wynika to z dobrych praktyk programistycznych.

## 2. Ogólne zasady

- Nie pracujemy bezpośrednio na branchu `main`.
- Każda większa zmiana powinna być wykonana na osobnym branchu.
- Zmiany do `main` trafiają przez Pull Request.
- Przed rozpoczęciem pracy warto sprawdzić, czy ktoś już nie robi podobnego zadania.
- Dokumentacja jest częścią projektu i podlega takim samym zasadom porządku jak kod.

## 3. Nazewnictwo branchy

Zalecany format:

```text
feature/nazwa-zadania
fix/nazwa-poprawki
docs/nazwa-dokumentu
test/nazwa-testu
```

Przykłady:
```text
feature/rejestracja-psa
fix/poprawa-walidacji-vin
docs/model-dziedziny
test/testy-api-zapisow
```

## 4. Commity

Commit powinien być:
- krótki,
- czytelny,
- opisujący realną zmianę.

Przykłady:
```text
feat: dodano strukturę katalogów projektu
docs: uzupełniono opis przypadków użycia
fix: poprawiono walidację danych psa
test: dodano scenariusze testowe API
```

## 5. Pull Request

Pull Request powinien:
- mieć jasny tytuł,
- opisywać, co zostało zmienione,
- wskazywać, czy zmiana dotyczy dokumentacji, backendu, testów lub organizacji,
- być możliwie mały i konkretny.

Nie wrzucamy jednego gigantycznego PR-a z wieloma różnymi tematami.

## 6. Struktura zmian

Obecnie repo ma prostą strukturę:
- instrukcje Git i GitHub -> `START_TUTAJ_GIT/`
- zasady współpracy i pliki główne -> katalog główny repo

Jeśli w kolejnych etapach pojawią się nowe obszary projektu, warto dodać dla nich osobne katalogi, np.:
- `docs/`
- `backend/`
- `frontend/`
- `testy/`

## 7. Czego nie robimy

- nie commitujemy bezpośrednio na `main`,
- nie wrzucamy przypadkowych plików,
- nie zostawiamy nieczytelnych nazw plików typu `nowy(1).md`,
- nie robimy `git push --force`, jeśli nie wiemy dokładnie, co robimy,
- nie mieszamy kilku różnych tematów w jednym PR.

## 8. Przed wysłaniem zmian

Przed `push` sprawdź:
- czy pliki są w dobrym katalogu,
- czy opis commita ma sens,
- czy nie dodałeś śmieciowych plików,
- czy zmiana nie psuje istniejącej struktury repo.

Pomocniczo:
```bash
git status
git diff --staged
```

## 9. Dla osób początkujących

Jeśli pierwszy raz używasz Gita, zacznij od folderu:

`START_TUTAJ_GIT/`

## 10. Najważniejsza zasada

Jak nie jesteś pewny, gdzie coś wrzucić albo jak nazwać branch, to zapytaj przed wrzuceniem bałaganu.
