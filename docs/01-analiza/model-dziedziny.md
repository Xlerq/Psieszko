# Model dziedziny i agregaty

## Konteksty dziedzinowe

Encje modelu zostały podzielone na następujące konteksty:

| Kontekst | Odpowiedzialność | Encje / elementy modelu |
|---|---|---|
| Karta właściciela | Dane właściciela i jego powiązania z psami | Karta właściciela, Właściciel |
| Dane psa | Podstawowe dane psa oraz jego opis domenowy | Pies |
| Usługa / Rezerwacja | Organizacja pobytu psa i rozliczenie usługi | Rezerwacja, Płatność |
| Lekcja | Organizacja i przebieg zajęć szkoleniowych | Lekcja, Temat, Strefa nauki, Wyposażenie szkoleniowe |
| Dziennik dnia | Rejestrowanie przebiegu dnia psa w placówce | Dziennik dnia, Aktywność, Incydent, Posiłek |
| Zdrowie | Dokumentacja zdrowotna psa | Karta zdrowia, Szczepienie, Lek, Wizyta weterynaryjna, Weterynarz |
| Struktura przedszkola | Organizacja placówki i pracowników | Przedszkole, Pracownik, Rola |

## Encje modelu dziedziny

Poniższa tabela opisuje encje na poziomie analitycznego modelu dziedziny, zgodnie z diagramami z `docs/01-analiza`.

| Nr | Encja | Kontekst | Opis |
|---:|---|---|---|
| 1 | Karta właściciela | Karta właściciela | Pełny agregat `OwnerCard`, który przechowuje dane właściciela i referencje do psów. |
| 2 | Właściciel | Karta właściciela | Osoba odpowiedzialna za psa i korzystająca z usług placówki. |
| 3 | Pies | Dane psa | Zwierzę korzystające z usług przedszkola, opieki i szkolenia. |
| 4 | Rezerwacja | Usługa / Rezerwacja | Zgłoszenie pobytu psa w placówce w określonym terminie. |
| 5 | Płatność | Usługa / Rezerwacja | Element rozliczenia finansowego powiązany z rezerwacją. Na diagramie występuje jako element modelu dziedziny. |
| 6 | Lekcja | Lekcja | Pojedyncze zajęcia szkoleniowe realizowane dla psa. |
| 7 | Temat | Lekcja | Zakres lub rodzaj szkolenia realizowanego podczas lekcji. |
| 8 | Strefa nauki | Lekcja | Miejsce przeznaczone do prowadzenia zajęć szkoleniowych. |
| 9 | Wyposażenie szkoleniowe | Lekcja | Zasób wykorzystywany podczas lekcji, np. przeszkody, zabawki albo akcesoria treningowe. |
| 10 | Dziennik dnia | Dziennik dnia | Zapis przebiegu pobytu psa w danym dniu. |
| 11 | Aktywność | Dziennik dnia | Czynność albo ćwiczenie wykonane podczas pobytu lub lekcji. |
| 12 | Incydent | Dziennik dnia | Nieprzewidziane zdarzenie związane z pobytem psa lub przebiegiem zajęć. |
| 13 | Posiłek | Dziennik dnia | Karmienie psa w trakcie pobytu w placówce. |
| 14 | Karta zdrowia | Zdrowie | Dokumentacja medyczna psa. |
| 15 | Szczepienie | Zdrowie | Wpis dotyczący wykonanego szczepienia psa. |
| 16 | Lek | Zdrowie | Preparat podawany psu w ramach leczenia lub opieki weterynaryjnej. |
| 17 | Wizyta weterynaryjna | Zdrowie | Konsultacja lub badanie psa wykonane przez weterynarza. |
| 18 | Weterynarz | Zdrowie | Osoba odpowiedzialna za opiekę zdrowotną nad zwierzętami. |
| 19 | Pracownik | Struktura przedszkola | Osoba zatrudniona w placówce. |
| 20 | Rola | Struktura przedszkola | Zakres obowiązków i uprawnień pracownika. |
| 21 | Przedszkole | Struktura przedszkola | Placówka świadcząca usługi opieki i szkolenia psów. |

## Elementy opisowe i techniczne

Poniższe elementy są ważne w modelu, ale nie są traktowane jako samodzielne agregaty DDD.

| Element | Typ w projekcie / modelu | Uzasadnienie |
|---|---|---|
| Rasa | enum | Opisuje rasę psa, ale nie ma własnego cyklu życia jako agregat. |
| Dieta | enum | Opisuje sposób żywienia psa, ale nie jest osobnym agregatem. |
| Cechy zwierzęcia | enum / cechy opisowe | Oznaczają cechy typu `FRIENDLY`, `SHY`, `AGGRESSIVE`, `PLAYFUL`, `CALM`. |
| Profil behawioralny | obiekt osadzany | Jest częścią danych psa i opisuje jego zachowanie. |
| Płatność | element agregatu `Reservation` | Na diagramie występuje jako element modelu dziedziny, ale projektowo należy do agregatu rezerwacji. |

## Agregaty DDD

Ta sekcja opisuje projektowe granice agregatów. Nie należy jej czytać jako prostego odwzorowania wszystkich połączeń z diagramu analitycznego. Diagram pokazuje relacje dziedzinowe, natomiast agregaty pokazują granice spójności i odpowiedzialności w projekcie.

### 1. Agregat `OwnerCard`

**Korzeń agregatu:** `OwnerCard` / Karta właściciela

**Zawiera:**
- kartę właściciela,
- dane właściciela.

**Referencje:**
- referencje do psów właściciela.

`OwnerCard` jest pełnym agregatem w projekcie. Przechowuje właściciela i referencje do psów, ale `Pies` nie jest encją wewnętrzną agregatu `OwnerCard`. Dzięki temu karta właściciela może wskazywać psy bez przejmowania odpowiedzialności za ich pełny cykl życia.

### 2. Agregat `Dog` / `Pies`

**Korzeń agregatu:** `Pies`

**Zawiera:**
- dane identyfikacyjne psa,
- dane opisowe psa,
- rasę jako enum,
- dietę jako enum,
- cechy zwierzęcia,
- profil behawioralny jako obiekt osadzany.

**Referencje:**
- właściciel / karta właściciela,
- karta zdrowia,
- rezerwacje.

Agregat psa odpowiada za spójność danych samego psa. Właściciel, karta zdrowia i rezerwacje są z nim powiązane, ale nie są encjami wewnętrznymi agregatu `Pies`. Należą do osobnych kontekstów albo agregatów.

### 3. Agregat `Reservation` / `Rezerwacja`

**Korzeń agregatu:** `Rezerwacja`

**Zawiera:**
- rezerwację,
- płatność / `Payment` jako element rozliczenia rezerwacji.

**Referencje:**
- pies,
- lekcje,
- dane zdrowotne lub wizyta weterynaryjna, jeżeli są potrzebne do obsługi pobytu.

Agregat `Reservation` odpowiada za organizację pobytu oraz rozliczenie usługi. `Payment` należy do tego agregatu. Lekcja i zdrowie są osobnymi kontekstami, dlatego `Lekcja` i `Wizyta weterynaryjna` nie są encjami wewnętrznymi agregatu rezerwacji, mimo że mogą być z rezerwacją powiązane.

### 4. Agregat `Lesson` / `Lekcja`

**Korzeń agregatu:** `Lekcja`

**Zawiera / zarządza:**
- lekcją,
- tematem lekcji,
- strefą nauki,
- wykorzystanym wyposażeniem szkoleniowym.

**Referencje:**
- pies,
- pracownik prowadzący lekcję,
- rezerwacja, jeżeli lekcja odbywa się w ramach pobytu.

Agregat lekcji odpowiada za przebieg zajęć szkoleniowych. Jest osobnym kontekstem względem rezerwacji, ponieważ rezerwacja organizuje pobyt, a lekcja opisuje część szkoleniową.

### 5. Agregat `DailyJournal` / `Dziennik dnia`

**Korzeń agregatu:** `Dziennik dnia`

**Zawiera:**
- aktywności,
- incydenty,
- posiłki.

Jest to agregat złożony z więcej niż jednej encji. Dziennik dnia dokumentuje przebieg pobytu psa i grupuje zdarzenia z danego dnia.

### 6. Agregat `HealthCard` / `Karta zdrowia`

**Korzeń agregatu:** `Karta zdrowia`

**Zawiera:**
- szczepienia,
- leki,
- wizyty weterynaryjne.

**Referencje:**
- pies,
- weterynarz.

Agregat zdrowia odpowiada za dokumentację medyczną psa. Weterynarz może być powiązany z wizytą, ale nie musi być encją wewnętrzną karty zdrowia, jeżeli jest zarządzany osobno.

### 7. Agregat `Kindergarten` / `Przedszkole`

**Korzeń agregatu:** `Przedszkole`

**Zawiera / zarządza:**
- pracownikami,
- rolami pracowników.

Agregat przedszkola opisuje strukturę organizacyjną placówki. Pracownik ma przypisaną rolę, która określa zakres obowiązków i uprawnień.

## Agregat złożony z więcej niż jednej encji

Przykładem agregatu złożonego z więcej niż jednej encji jest `DailyJournal`:

- `Dziennik dnia` jako korzeń agregatu,
- `Aktywność` jako element dziennika,
- `Incydent` jako element dziennika,
- `Posiłek` jako element dziennika.

Drugim przykładem jest `HealthCard`:

- `Karta zdrowia` jako korzeń agregatu,
- `Szczepienie`,
- `Lek`,
- `Wizyta weterynaryjna`.

## Relacje między encjami

- `Karta właściciela` przechowuje dane właściciela i referencje do psów.
- `Właściciel` może być powiązany z jednym albo wieloma psami.
- `Pies` jest powiązany z kartą właściciela, ale nie jest częścią agregatu `OwnerCard`.
- `Pies` może posiadać dane opisowe takie jak rasa, dieta, cechy zwierzęcia i profil behawioralny.
- `Pies` może mieć wiele rezerwacji.
- `Rezerwacja` dotyczy pobytu psa w określonym terminie.
- `Rezerwacja` zawiera rozliczenie w postaci `Płatności` / `Payment`.
- `Rezerwacja` może być powiązana z lekcjami, ale lekcje należą do osobnego kontekstu.
- `Lekcja` ma temat i odbywa się w strefie nauki.
- `Lekcja` może wykorzystywać wyposażenie szkoleniowe.
- `Lekcja` może być prowadzona przez pracownika.
- `Dziennik dnia` dokumentuje przebieg dnia psa w placówce.
- `Dziennik dnia` zawiera aktywności, incydenty i posiłki.
- `Karta zdrowia` jest powiązana z psem.
- `Karta zdrowia` zawiera szczepienia, leki i wizyty weterynaryjne.
- `Wizyta weterynaryjna` może być powiązana z weterynarzem.
- `Przedszkole` zatrudnia pracowników.
- `Pracownik` posiada rolę.
- `Pracownik` może prowadzić lekcje lub obsługiwać procesy pobytu psa.
