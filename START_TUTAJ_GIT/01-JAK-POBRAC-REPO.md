# 01 — jak pobrać repo na komputer

## Co musisz mieć

Na komputerze musi być zainstalowany Git.

Sprawdzenie:

```bash
git --version
```

Jeśli pokazuje wersję, to jest OK.

Jeśli nie działa, na Arch Linuxie:

```bash
sudo pacman -S git
```

## Jak pobrać repo

1. Wejdź na stronę repozytorium na GitHubie.
2. Kliknij zielony przycisk **Code**.
3. Skopiuj adres repo.
4. Otwórz terminal.
5. Wpisz:

```bash
git clone ADRES_REPO
```

Przykład:

```bash
git clone https://github.com/TWOJ_LOGIN/psie_przedszkole.git
```

## Jak wejść do folderu projektu

Po pobraniu wpisz:

```bash
cd psie_przedszkole
```

## Jak sprawdzić, czy jesteś w dobrym miejscu

Wpisz:

```bash
ls
```

Powinieneś widzieć pliki projektu, np.:
- `README.md`
- `CONTRIBUTING.md`
- katalog `docs/`
- katalog `backend/`

## Jak sprawdzić, na jakim jesteś branchu

```bash
git branch
```

Przy aktualnym branchu będzie gwiazdka `*`.

Jeśli widzisz:

```text
* main
```

to znaczy, że jesteś na głównym branchu.

## Co zrobić przed rozpoczęciem pracy

Zawsze najpierw zaktualizuj `main`:

```bash
git checkout main
git pull origin main
```

Dopiero potem zrób własny branch.

## Jak zrobić własny branch

Przykład dla dokumentacji:

```bash
git checkout -b docs/opis-projektu
```

Przykład dla backendu:

```bash
git checkout -b feature/rejestracja-psa
```

## Po czym poznać, że branch się udał

Wpisz:

```bash
git branch
```

Powinieneś zobaczyć coś takiego:

```text
  main
* docs/opis-projektu
```

Gwiazdka ma być przy Twoim branchu.

## Najkrótszy schemat początku pracy

```bash
git clone ADRES_REPO
cd psie_przedszkole
git checkout main
git pull origin main
git checkout -b docs/opis-projektu
```

Następny plik:

`02-JAK-ZROBIC-ZMIANY-I-WRZUCIC-NA-GITHUB.md`
