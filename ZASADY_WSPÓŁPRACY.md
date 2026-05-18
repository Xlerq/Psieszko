# Zasady współpracy w repozytorium

Ten plik zawiera zasady pracy dla contributorów projektu **Psieszko**. Każda osoba dostaje jedno konkretne zadanie, robi je na osobnym branchu i po zakończeniu otwiera Pull Request do `main`.

Szczegółowa lista issue i kolejność prac są prowadzone osobno przez Project Managera. Contributor nie musi znać całego backlogu, tylko swoje aktualne zadanie.

## 1. Język projektu

W projekcie używamy **języka polskiego** w:
- dokumentacji,
- nazwach zadań,
- opisach Pull Requestów,
- opisach zadań przekazywanych przez Project Managera,
- komentarzach organizacyjnych.

Kod piszemy **po angielsku**:
- nazwy klas,
- nazwy metod,
- nazwy pól,
- nazwy pakietów,
- komunikaty techniczne, jeśli są częścią API lub kodu.

## 2. Ogólne zasady

- Nie pracujemy bezpośrednio na branchu `main`.
- Każde zadanie robimy na osobnym branchu.
- Zmiany do `main` trafiają przez Pull Request.
- Jedno zadanie = jeden branch = jeden Pull Request.
- Nie bierzemy losowego zadania z repozytorium bez ustalenia z Project Managerem.
- Dokumentacja jest częścią projektu i podlega takim samym zasadom porządku jak kod.


## 3. Nazewnictwo branchy

Zalecany format branchy:

```text
feature/nazwa-zadania
fix/nazwa-poprawki
docs/nazwa-dokumentu
test/nazwa-testu
```

Przykłady:

```text
feature/rejestracja-psa
fix/poprawa-walidacji
docs/model-dziedziny
test/testy-api-zapisow
```

Nazwa brancha ma jasno mówić, nad czym pracujesz. Nie musi być idealna, ale ma być czytelna.

## 4. Commity

Commit powinien być:
- krótki,
- czytelny,
- opisujący realną zmianę.

Przykłady:

```text
feat: dodano strukture katalogow projektu
docs: uzupelniono opis przypadkow uzycia
fix: poprawiono walidacje danych psa
test: dodano scenariusze testowe API
```

## 5. Pull Request

Pull Request powinien:
- mieć jasny tytuł,
- opisywać, co zostało zmienione,
- wskazywać, czy zmiana dotyczy dokumentacji, backendu, testów lub organizacji,
- być możliwie mały i konkretny.

Nie wrzucamy jednego gigantycznego PR-a z wieloma różnymi tematami.

Po otwarciu PR nie zakładasz drugiego PR dla poprawek. Poprawiasz ten sam branch i robisz kolejny `push`.

## 6. Struktura zmian

Przed dodaniem nowego pliku sprawdź, czy trafia do właściwego katalogu:
- organizacja -> `docs/00-organizacja/`
- analiza -> `docs/01-analiza/`
- projekt -> `docs/02-projekt/`
- API -> `docs/03-api/`
- testy -> `docs/04-testy/`
- prezentacja -> `docs/05-prezentacja/`
- instrukcje -> `START_TUTAJ_GIT/`
- kod -> `src/`
- pliki uruchomieniowe i środowiskowe -> `infra/`

## 7. Dokumentacja i testy

Jeśli zmieniasz API albo model domeny, zaktualizuj dokumentację.

Jeśli dodajesz endpoint REST, zadanie powinno docelowo mieć:
- endpoint widoczny w Swaggerze,
- walidację requestu,
- obsługę błędów,
- test lub request w Bruno, jeśli zadanie tego dotyczy.

Przed wysłaniem zmian uruchom:

```bash
./gradlew test
```

Jeśli testy nie przechodzą, napisz o tym w opisie PR.

## 8. Definition of Done

Zadanie jest gotowe do PR, gdy:
- kod się kompiluje,
- testy przechodzą albo w PR jest jasno napisane, dlaczego nie przechodzą,
- zmiana dotyczy tylko przydzielonego zadania,
- dokumentacja została zaktualizowana, jeśli zmiana jej dotyczy,
- nie ma przypadkowych plików w commicie,
- branch ma czytelną nazwę.

## 9. Czego nie robimy

- nie commitujemy bezpośrednio na `main`,
- nie wrzucamy przypadkowych plików,
- nie zostawiamy nieczytelnych nazw plików typu `nowy(1).md`,
- nie robimy `git push --force`, jeśli nie wiemy dokładnie, co robimy,
- nie mieszamy kilku różnych tematów w jednym PR,
- nie edytujemy prywatnej listy issue Project Managera bez ustalenia.

## 10. Przed wysłaniem zmian

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

## 12. Dla osób początkujących

Jeśli pierwszy raz używasz Gita, zacznij od folderu:

`START_TUTAJ_GIT/`

## 13. Najważniejsza zasada

Jak nie jesteś pewny, gdzie coś wrzucić albo jak nazwać branch, zapytaj przed wrzuceniem bałaganu.
