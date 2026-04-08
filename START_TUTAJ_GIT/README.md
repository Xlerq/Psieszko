# START TUTAJ — instrukcje Gita i GitHuba

Jeżeli **pierwszy raz w życiu** używasz Gita albo GitHuba, to **zacznij od tego folderu**.

Nie musisz nic wiedzieć wcześniej.
Poniższe pliki są napisane krok po kroku, bardzo prosto.

## Kolejność czytania

1. `00-NAJPIERW-PRZECZYTAJ-TO.md`
2. `01-JAK-POBRAC-REPO.md`
3. `02-JAK-ZROBIC-ZMIANY-I-WRZUCIC-NA-GITHUB.md`
4. `03-JAK-ZROBIC-PULL-REQUEST.md`
5. `04-NAJCZESTSZE-BLEDY.md`
6. `05-SCIAGA-KOMEND.md`

## Najkrótsza wersja

Nigdy nie pracuj bezpośrednio na `main`.

Zawsze rób tak:

```bash
git checkout main
git pull origin main
git checkout -b docs/nazwa-zmiany

# robisz zmiany

git status
git add .
git commit -m "docs: opis zmiany"
git push -u origin docs/nazwa-zmiany
```

Potem wchodzisz na GitHub i robisz **Pull Request**.
