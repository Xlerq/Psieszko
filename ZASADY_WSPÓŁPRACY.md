# Zasady wspolpracy w repozytorium

Ten plik laczy w jednym miejscu najwazniejsze zasady pracy z repozytorium projektu **psie_przedszkole** oraz krotka instrukcje startowa dla osob, ktore dopiero dolaczaja do pracy.

## Start

Jesli dopiero zaczynasz:

1. Zaktualizuj lokalne repozytorium:

```bash
git checkout main
git pull origin main
```

2. Utworz osobny branch:

```bash
git checkout -b docs/nazwa-zmiany
```

3. Wprowadz zmiany i sprawdz status:

```bash
git status
```

4. Dodaj pliki i utworz commit:

```bash
git add .
git commit -m "docs: opis zmiany"
```

5. Wypchnij branch:

```bash
git push -u origin docs/nazwa-zmiany
```

6. Otworz Pull Request na GitHubie.

## 1. Jezyk projektu

W projekcie uzywamy **jezyka polskiego** w:
- dokumentacji,
- nazwach zadan,
- opisach Pull Requestow,
- opisach issue,
- komentarzach organizacyjnych.

Kod moze zawierac nazewnictwo techniczne po angielsku, jesli wynika to z dobrych praktyk programistycznych.

## 2. Ogolne zasady

- Nie pracujemy bezposrednio na branchu `main`.
- Kazda wieksza zmiana powinna byc wykonana na osobnym branchu.
- Zmiany do `main` trafiaja przez Pull Request.
- Przed rozpoczeciem pracy warto sprawdzic, czy ktos juz nie robi podobnego zadania.
- Dokumentacja jest czescia projektu i podlega takim samym zasadom porzadku jak kod.

## 3. Nazewnictwo branchy

Zalecany format:

```text
feature/nazwa-zadania
fix/nazwa-poprawki
docs/nazwa-dokumentu
test/nazwa-testu
```

Przyklady:

```text
feature/rejestracja-psa
fix/poprawa-walidacji
docs/model-dziedziny
test/testy-api-zapisow
```

## 4. Commity

Commit powinien byc:
- krotki,
- czytelny,
- opisujacy realna zmiane.

Przyklady:

```text
feat: dodano strukture katalogow projektu
docs: uzupelniono opis przypadkow uzycia
fix: poprawiono walidacje danych psa
test: dodano scenariusze testowe API
```

## 5. Pull Request

Pull Request powinien:
- miec jasny tytul,
- opisywac, co zostalo zmienione,
- wskazywac, czy zmiana dotyczy dokumentacji, backendu, testow lub organizacji,
- byc mozliwie maly i konkretny.

Nie wrzucamy jednego gigantycznego PR-a z wieloma roznymi tematami.

## 6. Struktura zmian

Przed dodaniem nowego pliku sprawdz, czy trafia do wlasciwego katalogu:
- organizacja -> `docs/00-organizacja/`
- analiza -> `docs/01-analiza/`
- projekt -> `docs/02-projekt/`
- API -> `docs/03-api/`
- testy -> `docs/04-testy/`
- prezentacja -> `docs/05-prezentacja/`
- instrukcje -> `START_TUTAJ_GIT/`
- kod -> `backend/`
- pliki uruchomieniowe i srodowiskowe -> `infra/`

## 7. Czego nie robimy

- nie commitujemy bezposrednio na `main`,
- nie wrzucamy przypadkowych plikow,
- nie zostawiamy nieczytelnych nazw plikow typu `nowy(1).md`,
- nie robimy `git push --force`, jesli nie wiemy dokladnie, co robimy,
- nie mieszamy kilku roznych tematow w jednym PR.

## 8. Przed wyslaniem zmian

Przed `push` sprawdz:
- czy pliki sa w dobrym katalogu,
- czy opis commita ma sens,
- czy nie dodales smieciowych plikow,
- czy zmiana nie psuje istniejacej struktury repo.

Pomocniczo:

```bash
git status
git diff --staged
```

## 9. Dla osob poczatkujacych

Jesli pierwszy raz uzywasz Gita, zacznij od folderu:

`START_TUTAJ_GIT/`

## 10. Najwazniejsza zasada

Jak nie jestes pewny, gdzie cos wrzucic albo jak nazwac branch, to zapytaj przed wrzuceniem balaganu.
