# CONTRIBUTING

Ten plik jest krotkim punktem startowym do pracy z repozytorium.

Pelne zasady wspolpracy znajdziesz w [`ZASADY_WSPÓŁPRACY.md`](./ZASADY_WSPÓŁPRACY.md), a instrukcje dla osob poczatkujacych w [`START_TUTAJ_GIT/README.md`](./START_TUTAJ_GIT/README.md).

## Zanim zaczniesz

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

## Najwazniejsze zasady

- nie pracuj bezposrednio na `main`,
- trzymaj sie struktury katalogow z `README.md`,
- nie wrzucaj przypadkowych plikow lokalnych,
- opisuj commity jasno i konkretnie,
- w razie watpliwosci sprawdz instrukcje albo zapytaj zespol.
