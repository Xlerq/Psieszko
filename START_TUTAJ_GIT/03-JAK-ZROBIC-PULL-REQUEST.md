# 03 — jak zrobić Pull Request

## Co to jest Pull Request

Pull Request to prośba:

> "Hej, zrobiłem zmiany na swoim branchu. Sprawdźcie je i dołączcie do `main`."

To jest normalny etap pracy.

## Kiedy robisz Pull Request

Po tym, jak:
- zrobiłeś zmiany,
- zrobiłeś commit,
- zrobiłeś push na GitHub.

## Jak zrobić Pull Request na GitHubie

1. Wejdź na stronę repozytorium.
2. GitHub często pokaże przycisk typu **Compare & pull request**.
3. Kliknij ten przycisk.
4. Upewnij się, że:
   - `base` to `main`,
   - `compare` to Twój branch.
5. Uzupełnij opis.
6. Kliknij **Create pull request**.

## Co wpisać w Pull Requeście

Napisz prosto:
- co zrobiłeś,
- jakie pliki zmieniłeś,
- czy to dokumentacja, backend, testy czy coś innego.

Przykład:

```text
Dodano pierwsza wersje opisu projektu i zakresu projektu.
Zmiany dotycza dokumentacji organizacyjnej.
```

## Co dalej

Ktoś sprawdzi Twoje zmiany.
Może:
- zaakceptować,
- poprosić o poprawki,
- napisać komentarz.

To normalne.
To nie znaczy, że zrobiłeś coś źle.

## Jeśli ktoś chce poprawki

1. Zostań na tym samym branchu.
2. Popraw pliki.
3. Zrób kolejny commit.
4. Zrób kolejny push.

Przykład:

```bash
git add .
git commit -m "docs: poprawiono opis projektu po uwagach"
git push
```

Nie musisz robić nowego Pull Requesta.
Ten sam PR się zaktualizuje.

## Najczęstsza pomyłka

Ludzie mylą:
- `push` — wysłanie brancha na GitHub,
- `Pull Request` — prośbę o dołączenie zmian do `main`.

To nie jest to samo.

Najpierw robisz `push`, potem Pull Request.

## Prosty schemat

```text
zmiany -> commit -> push -> Pull Request
```

Następny plik:

`04-NAJCZESTSZE-BLEDY.md`
