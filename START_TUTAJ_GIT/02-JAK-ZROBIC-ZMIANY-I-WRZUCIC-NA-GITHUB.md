# 02 — jak zrobić zmiany i wrzucić je na GitHub

Załóżmy, że:
- jesteś już w folderze projektu,
- masz swój branch,
- zmieniłeś jakiś plik.

## Krok 1 — sprawdź, co się zmieniło

```bash
git status
```

To pokazuje:
- jakie pliki zmieniłeś,
- jakie są nowe,
- co jeszcze nie zostało dodane do commita.

## Krok 2 — dodaj zmiany

Najprościej:

```bash
git add .
```

To dodaje wszystkie zmiany z bieżącego folderu.

Jeśli chcesz dodać tylko jeden plik:

```bash
git add README.md
```

## Krok 3 — zrób commit

Commit to zapis zmian z komentarzem.

Przykład:

```bash
git commit -m "docs: dodano opis projektu"
```

Inne przykłady:

```bash
git commit -m "feat: dodano endpoint rejestracji psa"
git commit -m "fix: poprawiono walidacje numeru telefonu"
git commit -m "test: dodano scenariusze testowe API"
```

## Krok 4 — wyślij branch na GitHub

```bash
git push -u origin NAZWA_TWOJEGO_BRANCHA
```

Przykład:

```bash
git push -u origin docs/opis-projektu
```

## Po czym poznasz, że push się udał

Git pokaże komunikat bez błędu.
Często pojawi się też link do utworzenia Pull Requesta.

## Najczęstszy pełny schemat

```bash
git status
git add .
git commit -m "docs: dodano opis projektu"
git push -u origin docs/opis-projektu
```

## Co zrobić, gdy commit nie działa

Czasem Git krzyczy, że nie zna Twojej nazwy i maila.
Wtedy ustaw to raz:

```bash
git config --global user.name "Twoje Imie Nazwisko"
git config --global user.email "twoj_mail@example.com"
```

Potem spróbuj jeszcze raz zrobić commit.

## Co zrobić, gdy push nie działa

Najczęstsze powody:
- nie masz dostępu do repo,
- źle wpisałeś nazwę brancha,
- nie zrobiłeś jeszcze commita,
- GitHub prosi o logowanie lub klucz SSH.

Najpierw sprawdź:

```bash
git status
git branch
```

## Nie rób tego

Nie rób od razu:

```bash
git push origin main
```

Jeśli nie wiesz dokładnie, co robisz.

W naszym projekcie pracujesz na **własnym branchu**, nie na `main`.

Następny plik:

`03-JAK-ZROBIC-PULL-REQUEST.md`
