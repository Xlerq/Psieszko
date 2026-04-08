# Git — pierwsze kroki

Ten plik jest dla osób, które pierwszy raz pracują z Gitem i GitHubem.

## 1. Co to jest Git

Git to system kontroli wersji.  
Pozwala śledzić zmiany w plikach i pracować zespołowo nad jednym projektem.

## 2. Co to jest GitHub

GitHub to miejsce, gdzie repozytorium jest przechowywane online.  
Tam wrzucamy zmiany, robimy Pull Requesty i przeglądamy historię projektu.

## 3. Podstawowe pojęcia

### Repozytorium
Folder projektu śledzony przez Git.

### Commit
Zapisanie zestawu zmian.

### Branch
Osobna gałąź pracy nad konkretną zmianą.

### Pull Request
Prośba o dołączenie zmian z Twojego brancha do głównej gałęzi projektu.

## 4. Pierwsze pobranie projektu

Skopiuj repozytorium na swój komputer:

```bash
git clone <ADRES_REPO>
cd psie_przedszkole
```

## 5. Utworzenie własnego brancha

Nigdy nie pracuj bezpośrednio na `main`.

Utwórz własny branch:

```bash
git checkout -b feature/nazwa-zadania
```

Przykład:
```bash
git checkout -b docs/opis-projektu
```

## 6. Dodawanie zmian

Po edycji plików sprawdź stan repo:

```bash
git status
```

Dodaj pliki do commita:

```bash
git add .
```

Możesz też dodać konkretny plik:

```bash
git add README.md
```

## 7. Commit

Zapisz zmiany:

```bash
git commit -m "docs: dodano pierwsza wersje README"
```

## 8. Wysłanie zmian na GitHub

```bash
git push -u origin feature/nazwa-zadania
```

Przykład:
```bash
git push -u origin docs/opis-projektu
```

## 9. Pull Request

Po wypchnięciu zmian na GitHub:
1. wejdź na stronę repo,
2. GitHub zwykle pokaże przycisk do utworzenia Pull Requesta,
3. kliknij,
4. opisz zmiany,
5. wyślij Pull Request.

## 10. Jak pobrać najnowsze zmiany z `main`

Przed rozpoczęciem nowej pracy dobrze jest zaktualizować lokalne repo:

```bash
git checkout main
git pull origin main
```

Potem utwórz nowy branch:

```bash
git checkout -b feature/nowe-zadanie
```

## 11. Najczęstsze komendy

### Sprawdzenie statusu
```bash
git status
```

### Sprawdzenie branchy
```bash
git branch
```

### Przejście na branch
```bash
git checkout nazwa-brancha
```

### Pobranie zmian z repo
```bash
git pull origin main
```

## 12. Czego nie robić

- nie pracuj bezpośrednio na `main`,
- nie rób `git push --force`, jeśli nie wiesz po co,
- nie wrzucaj wszystkich zmian świata w jednym commicie,
- nie ignoruj komunikatów błędów.

## 13. Typowy schemat pracy

```bash
git checkout main
git pull origin main
git checkout -b docs/nowy-dokument

# edycja plikow

git status
git add .
git commit -m "docs: dodano nowy dokument"
git push -u origin docs/nowy-dokument
```

## 14. Gdy coś się posypie

Najpierw:
```bash
git status
```

To bardzo często mówi, co jest nie tak.

Jeśli nadal nie wiesz:
- zrób zrzut ekranu,
- pokaż komunikat błędu,
- nie klikaj i nie wpisuj losowych komend z internetu.
