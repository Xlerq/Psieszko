# 00 — najpierw przeczytaj to

Ten plik jest dla osób, które:
- pierwszy raz używają Gita,
- pierwszy raz używają GitHuba,
- nie wiedzą, co to jest branch, commit i pull request,
- boją się coś zepsuć.

To normalne.

## Co musisz wiedzieć na start

### 1. Git to nie GitHub

**Git** to program na komputerze, który pilnuje zmian w plikach.

**GitHub** to strona internetowa, gdzie repozytorium siedzi online.

Czyli:
- na komputerze pracujesz Gitem,
- do internetu wysyłasz zmiany na GitHub.

### 2. Repozytorium to po prostu folder projektu

Repozytorium to folder, w którym jest projekt.
W naszym przypadku to repo projektu **psie_przedszkole**.

### 3. Nigdy nie pracuj bezpośrednio na `main`

To jest najważniejsza zasada.

Branch `main` to główna wersja projektu.
Na nim **nie dłubiesz bezpośrednio**.

Najpierw robisz własny branch, np.:

```bash
git checkout -b docs/opis-przypadkow-uzycia
```

Dopiero tam robisz zmiany.

### 4. Commit to zapis zmian

Commit = zapisanie zmian z komentarzem.

Przykład:

```bash
git commit -m "docs: dodano opis przypadku uzycia rejestracji psa"
```

### 5. Pull Request to prośba o dołączenie zmian

Po zrobieniu zmian wrzucasz branch na GitHub.
Potem robisz **Pull Request**, czyli prosisz o dołączenie zmian do `main`.

## Jedna rzecz, którą masz zapamiętać

Jeśli nic nie rozumiesz, to trzymaj się tego schematu:

1. pobierz repo,
2. wejdź do folderu,
3. zrób nowy branch,
4. zmień pliki,
5. zrób commit,
6. zrób push,
7. otwórz Pull Request.

## Czego NIE robić

- nie rób zmian bezpośrednio na `main`,
- nie wpisuj losowych komend z internetu,
- nie rób `git push --force`, jeśli nie wiesz co to jest,
- nie panikuj, tylko czytaj komunikat błędu.

## Dalej czytaj w tej kolejności

Następny plik:

`01-JAK-POBRAC-REPO.md`
