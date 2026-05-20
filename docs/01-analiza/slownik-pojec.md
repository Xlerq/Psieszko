# Słownik pojęć

## Pies
Pies to zwierzę korzystające z usług szkoły. Dla psa przechowywane są informacje identyfikacyjne, dane dotyczące zachowania, diety, zdrowia oraz historii pobytu i szkolenia. Pies jest powiązany z właścicielem, rasą, profilem behawioralnym oraz kartą zdrowia.

## Właściciel
Właściciel to osoba odpowiedzialna za psa i korzystająca z usług placówki. Właściciel posiada dane kontaktowe oraz może być powiązany z jednym lub wieloma psami.

## Karta właściciela
Karta właściciela reprezentuje pełny agregat OwnerCard w systemie. Zawiera rozszerzone informacje o właścicielu, historii współpracy oraz danych organizacyjnych związanych z obsługą klienta.

## Cechy zwierzęcia
Cechy zwierzęcia opisują charakter i zachowanie psa. Obejmują cechy takie jak FRIENDLY, SHY, AGGRESSIVE, PLAYFUL czy CALM.

## Rasa
Rasa określa przynależność psa do konkretnej grupy rasowej. W systemie rasa jest reprezentowana jako enum.

## Dieta
Dieta określa sposób żywienia psa, w tym alergie pokarmowe oraz zalecenia dotyczące karmienia. W systemie dieta jest reprezentowana jako enum.

## Profil behawioralny
Profil behawioralny zawiera informacje dotyczące zachowania psa, np. stosunku do innych zwierząt lub lęków separacyjnych. W systemie jest elementem osadzanym (@embeddable).

## Rezerwacja
Rezerwacja oznacza zgłoszenie pobytu psa w placówce w określonym terminie. Rezerwacja jest powiązana z psem oraz organizowanymi lekcjami.

## Lekcja
Lekcja oznacza pojedyncze zajęcia szkoleniowe realizowane dla psa podczas pobytu. Lekcja posiada temat, odbywa się w określonej strefie nauki i jest dokumentowana w dzienniku dnia.

## Temat
Temat określa zakres lub rodzaj szkolenia realizowanego podczas lekcji. Jeden temat może być wykorzystywany podczas wielu lekcji.

## Strefa nauki
Strefa nauki to miejsce przeznaczone do prowadzenia zajęć szkoleniowych. Każda strefa posiada określoną pojemność oraz przypisane wyposażenie szkoleniowe.

## Wyposażenie szkoleniowe
Wyposażenie szkoleniowe obejmuje przedmioty wykorzystywane podczas zajęć, np. przeszkody, zabawki lub akcesoria treningowe.

## Dziennik dnia
Dziennik dnia stanowi zapis przebiegu lekcji i pobytu psa w danym dniu. Zawiera informacje o aktywnościach, incydentach oraz posiłkach.

## Aktywność
Aktywność oznacza czynność lub ćwiczenie wykonane podczas lekcji szkoleniowej.

## Incydent
Incydent oznacza nieprzewidziane zdarzenie związane z pobytem psa lub przebiegiem zajęć.

## Posiłek
Posiłek reprezentuje karmienie psa podczas pobytu w placówce. Obejmuje informacje dotyczące ilości podanego pokarmu oraz stopnia spożycia posiłku.

## Karta zdrowia
Karta zdrowia to dokumentacja medyczna psa zawierająca historię leczenia, szczepień oraz przyjmowanych leków.

## Szczepienie
Szczepienie oznacza wpis dotyczący wykonanego szczepienia psa.

## Lek
Lek oznacza preparat podawany psu w ramach leczenia lub opieki weterynaryjnej.

## Wizyta weterynaryjna
Wizyta weterynaryjna oznacza konsultację lub badanie psa przeprowadzone przez weterynarza podczas pobytu w placówce.

## Weterynarz
Weterynarz to osoba odpowiedzialna za opiekę zdrowotną nad zwierzętami oraz prowadzenie dokumentacji medycznej.

## Pracownik
Pracownik to osoba zatrudniona w placówce odpowiedzialna za prowadzenie zajęć, opiekę nad psami lub obsługę systemu.

## Rola
Rola określa zakres obowiązków i uprawnień pracownika w systemie, np. trener, opiekun lub administrator.

## Płatność
Płatność reprezentuje rozliczenie finansowe za pobyt psa oraz wykonane usługi szkoleniowe. W systemie płatność jest elementem osadzanym (@embeddable).

## Przedszkole
Przedszkole reprezentuje placówkę świadczącą usługi opieki i szkolenia psów.
