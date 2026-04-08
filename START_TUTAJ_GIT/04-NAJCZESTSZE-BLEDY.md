# 04 — najczęstsze błędy

## 1. Robię zmiany na `main`

Źle.

Najpierw wróć na `main`, zaktualizuj go i zrób nowy branch:

```bash
git checkout main
git pull origin main
git checkout -b docs/moja-zmiana
```

## 2. Nie wiem, co zmieniłem

Wpisz:

```bash
git status
```

To jest pierwsza komenda ratunkowa.

## 3. Nie wiem, na jakim jestem branchu

```bash
git branch
```

Gwiazdka `*` pokazuje aktywny branch.

## 4. Commit nie działa

Najczęściej:
- nie dodałeś plików przez `git add`,
- Git nie zna Twojej nazwy i maila.

Sprawdź:

```bash
git status
```

Ustaw dane, jeśli trzeba:

```bash
git config --global user.name "Twoje Imie Nazwisko"
git config --global user.email "twoj_mail@example.com"
```

## 5. Push nie działa

Najpierw sprawdź:

```bash
git status
git branch
git remote -v
```

Potem przeczytaj dokładnie komunikat błędu.

## 6. Dodałem zły plik

Jeśli jeszcze nie zrobiłeś commita, możesz poprawić to przed commitem.
Najpierw zobacz, co jest dodane:

```bash
git status
```

## 7. Wrzuciłem za dużo zmian naraz

Źle.

Rób mniejsze zmiany i mniejsze PR-y.
Łatwiej to sprawdzić i mniej rzeczy się psuje.

## 8. Wpisałem jakąś dziwną komendę z internetu

Nie rób tak.

Jeśli nie rozumiesz komendy, nie wpisuj jej.
Najpierw zapytaj osobę ogarniającą repo.

## 9. Chcę zrobić `git push --force`

Nie.

Jeśli nie wiesz dokładnie, po co to jest, to tego nie używaj.
Można tym narobić syfu.

## 10. Nie wiem, co teraz zrobić

Zawsze zacznij od tych dwóch komend:

```bash
git status
git branch
```

To najczęściej daje już połowę odpowiedzi.

## Złota zasada

Jak coś się sypie:
- nie panikuj,
- nie klikaj losowo,
- przeczytaj błąd,
- zrób screen,
- pokaż komuś komunikat.
