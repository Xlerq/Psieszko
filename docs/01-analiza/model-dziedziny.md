# Model dziedziny i agregaty

## Konteksty dziedzinowe

Model dziedziny został podzielony na następujące konteksty:

1. **Karta właściciela** – dane właściciela oraz pełny agregat `OwnerCard`.
2. **Dane psa** – podstawowe dane psa, właściciel, cechy, rasa, dieta i profil behawioralny.
3. **Zdrowie** – karta zdrowia, szczepienia, leki, wizyty weterynaryjne i weterynarz.
4. **Usługa** – rezerwacja pobytu psa oraz rozliczenie usługi.
5. **Lekcja** – zajęcia szkoleniowe, tematy, strefy nauki i wyposażenie.
6. **Dziennik dnia** – zapis przebiegu dnia, aktywności, incydentów i posiłków.
7. **Struktura przedszkola** – przedszkole, pracownicy i role.

## Encje domenowe

| Nr | Encja | Kontekst | Opis |
|---:|---|---|---|
| 1 | Karta właściciela | Karta właściciela | Pełny agregat `OwnerCard`, grupujący informacje organizacyjne związane z właścicielem i jego psami. |
| 2 | Właściciel | Dane psa / Karta właściciela | Osoba odpowiedzialna za psa i korzystająca z usług placówki. |
| 3 | Pies | Dane psa | Zwierzę korzystające z usług przedszkola, szkolenia i opieki. |
| 4 | Rezerwacja | Usługa | Zgłoszenie pobytu psa w placówce w określonym terminie. |
| 5 | Lekcja | Lekcja | Pojedyncze zajęcia szkoleniowe realizowane dla psa podczas pobytu. |
| 6 | Temat | Lekcja | Rodzaj lub zakres szkolenia realizowanego podczas lekcji. |
| 7 | Strefa nauki | Lekcja | Miejsce prowadzenia zajęć szkoleniowych. |
| 8 | Wyposażenie szkoleniowe | Lekcja | Zasób wykorzystywany podczas lekcji, np. przeszkody, zabawki lub akcesoria treningowe. |
| 9 | Dziennik dnia | Dziennik dnia | Zapis przebiegu dnia psa w placówce. |
| 10 | Aktywność | Dziennik dnia | Czynność lub ćwiczenie wykonane podczas dnia albo lekcji. |
| 11 | Incydent | Dziennik dnia | Nieprzewidziane zdarzenie związane z pobytem psa lub zajęciami. |
| 12 | Posiłek | Dziennik dnia | Pojedyncze karmienie psa w trakcie pobytu. |
| 13 | Karta zdrowia | Zdrowie | Dokumentacja medyczna psa. |
| 14 | Szczepienie | Zdrowie | Wpis dotyczący wykonanego szczepienia psa. |
| 15 | Lek | Zdrowie | Preparat podawany psu w ramach leczenia lub opieki weterynaryjnej. |
| 16 | Wizyta weterynaryjna | Zdrowie | Konsultacja lub badanie psa wykonane przez weterynarza. |
| 17 | Weterynarz | Zdrowie | Osoba odpowiedzialna za opiekę zdrowotną nad zwierzętami. |
| 18 | Pracownik | Struktura przedszkola | Osoba zatrudniona w placówce. |
| 19 | Rola | Struktura przedszkola | Zakres obowiązków i uprawnień pracownika. |
| 20 | Przedszkole | Struktura przedszkola | Placówka świadcząca usługi opieki i szkolenia psów. |

## Elementy niebędące encjami

Poniższe pojęcia występują w modelu, ale nie są traktowane jako niezależne encje:

| Pojęcie | Typ | Uzasadnienie |
|---|---|---|
| Rasa | enum | Określa przynależność psa do rasy, ale nie posiada własnego cyklu życia jako encja. |
| Dieta | enum | Określa sposób żywienia psa, ale jest klasyfikacją, a nie niezależną encją. |
| Cechy zwierzęcia | enum / cechy opisowe | Oznaczają cechy typu `FRIENDLY`, `SHY`, `AGGRESSIVE`, `PLAYFUL`, `CALM`. |
| Profil behawioralny | `@embeddable` | Jest częścią danych psa i nie posiada samodzielnej tożsamości. |
| Płatność | `@embeddable` | Jest elementem rozliczenia usługi, ale nie jest niezależnym agregatem ani encją. |

## Agregaty

### 1. Agregat Karta właściciela

**Korzeń agregatu:** `Karta właściciela` / `OwnerCard`

**Encje w agregacie:**
- Karta właściciela,
- Właściciel,
- Pies.

Agregat reprezentuje pełną kartę właściciela i dane związane z obsługą klienta. Jest to agregat złożony z więcej niż jednej encji. Karta właściciela grupuje właściciela oraz psy przypisane do tego właściciela.

### 2. Agregat Pies

**Korzeń agregatu:** `Pies`

**Encje i elementy powiązane:**
- Pies,
- Właściciel,
- Karta zdrowia,
- Rezerwacja.

Pies jest głównym obiektem domenowym. Jest powiązany z właścicielem, kartą zdrowia i rezerwacjami. Dane takie jak rasa, dieta, cechy zwierzęcia i profil behawioralny opisują psa, ale nie są niezależnymi encjami.

### 3. Agregat Rezerwacja

**Korzeń agregatu:** `Rezerwacja`

**Encje w agregacie:**
- Rezerwacja,
- Lekcja,
- Wizyta weterynaryjna.

Rezerwacja organizuje pobyt psa w placówce. W ramach rezerwacji mogą zostać zaplanowane lekcje oraz opcjonalna wizyta weterynaryjna.

### 4. Agregat Lekcja

**Korzeń agregatu:** `Lekcja`

**Encje w agregacie:**
- Lekcja,
- Temat,
- Strefa nauki,
- Wyposażenie szkoleniowe,
- Pracownik.

Lekcja opisuje pojedyncze zajęcia szkoleniowe. Jest prowadzona przez pracownika, ma temat, odbywa się w strefie nauki i może korzystać z wyposażenia szkoleniowego.

### 5. Agregat Dziennik dnia

**Korzeń agregatu:** `Dziennik dnia`

**Encje w agregacie:**
- Dziennik dnia,
- Aktywność,
- Incydent,
- Posiłek.

Dziennik dnia dokumentuje przebieg pobytu psa. Zawiera wykonane aktywności, odnotowane incydenty oraz posiłki.

### 6. Agregat Karta zdrowia

**Korzeń agregatu:** `Karta zdrowia`

**Encje w agregacie:**
- Karta zdrowia,
- Szczepienie,
- Lek,
- Wizyta weterynaryjna,
- Weterynarz.

Karta zdrowia gromadzi dokumentację medyczną psa. Zawiera szczepienia, podawane leki oraz wizyty weterynaryjne.

### 7. Agregat Struktura przedszkola

**Korzeń agregatu:** `Przedszkole`

**Encje w agregacie:**
- Przedszkole,
- Pracownik,
- Rola.

Agregat opisuje strukturę organizacyjną placówki. Przedszkole zatrudnia pracowników, a każdy pracownik posiada przypisaną rolę określającą jego obowiązki i uprawnienia.

## Relacje między encjami

- Właściciel może posiadać jednego lub wielu psów.
- Karta właściciela grupuje dane właściciela i powiązane psy.
- Pies jest przypisany do właściciela oraz posiada kartę zdrowia.
- Pies może mieć wiele rezerwacji.
- Rezerwacja dotyczy jednego psa i może obejmować jedną lub wiele lekcji.
- Lekcja ma temat, odbywa się w strefie nauki i może wykorzystywać wyposażenie szkoleniowe.
- Lekcja może być powiązana z dziennikiem dnia.
- Dziennik dnia zawiera aktywności, incydenty i posiłki.
- Karta zdrowia psa zawiera szczepienia, leki i wizyty weterynaryjne.
- Wizyta weterynaryjna jest realizowana przez weterynarza.
- Przedszkole zatrudnia pracowników.
- Pracownik posiada rolę.
- Pracownik może prowadzić lekcje lub obsługiwać procesy związane z pobytem psa.

## Zgodność z diagramami

Dokument odwołuje się do diagramów z katalogu `docs/01-analiza`:
- `podzial.png` pokazuje relacje między pojęciami modelu dziedziny,
- `podzial_2.png` pokazuje podział na konteksty, m.in. kartę właściciela, dane psa, zdrowie, usługę, lekcję, dziennik dnia i strukturę przedszkola.

Dokument spełnia wymagania issue:
- opisuje minimum 20 encji,
- dzieli encje na konteksty,
- wskazuje więcej niż 3 agregaty,
- zawiera agregat złożony z więcej niż jednej encji,
- opisuje relacje między encjami,
- odwołuje się do diagramów z `docs/01-analiza`.
