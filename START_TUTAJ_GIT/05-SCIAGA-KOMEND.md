# 05 — ściąga komend

## Początek pracy

```bash
git checkout main
git pull origin main
git checkout -b docs/nazwa-zmiany
```

## Sprawdzenie sytuacji

```bash
git status
git branch
```

## Dodanie i zapisanie zmian

```bash
git add .
git commit -m "docs: opis zmiany"
```

## Wysłanie zmian

```bash
git push -u origin docs/nazwa-zmiany
```

## Po poprawkach do istniejącego PR

```bash
git add .
git commit -m "docs: poprawki po uwagach"
git push
```

## Pobranie aktualnego `main`

```bash
git checkout main
git pull origin main
```

## Sprawdzenie zdalnego repo

```bash
git remote -v
```

## Konfiguracja Gita, jeśli robisz to pierwszy raz

```bash
git config --global user.name "Twoje Imie Nazwisko"
git config --global user.email "twoj_mail@example.com"
```

## Najprostszy poprawny schemat pracy

```bash
git checkout main
git pull origin main
git checkout -b docs/moja-zmiana

# robisz zmiany

git status
git add .
git commit -m "docs: opis zmiany"
git push -u origin docs/moja-zmiana
```
