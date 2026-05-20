# Odkrywanie encji

| Pojęcie | Encja? | Uzasadnienie |
|---|---|---|
| Pies | TAK | Główny obiekt domenowy systemu |
| Właściciel | TAK | Reprezentuje klienta placówki |
| Karta właściciela | TAK | Reprezentuje agregat OwnerCard |
| Cechy zwierzęcia | NIE | Są elementem opisowym psa i reprezentowane jako enumy/cechy |
| Rasa | NIE | Reprezentowana jako enum |
| Dieta | NIE | Reprezentowana jako enum |
| Profil behawioralny | NIE | Reprezentowany jako @embeddable |
| Rezerwacja | TAK | Reprezentuje organizację pobytu psa |
| Lekcja | TAK | Stanowi podstawowy element procesu szkoleniowego |
| Temat | TAK | Definiuje rodzaj prowadzonych zajęć |
| Strefa nauki | TAK | Jest niezależnym miejscem realizacji zajęć |
| Wyposażenie szkoleniowe | TAK | Reprezentuje zasoby wykorzystywane podczas lekcji |
| Dziennik dnia | TAK | Przechowuje historię przebiegu pobytu w ciągu dnia |
| Aktywność | TAK | Opisuje działania wykonywane podczas lekcji |
| Incydent | TAK | Rejestruje nieprzewidziane zdarzenia |
| Posiłek | TAK | Reprezentuje pojedyncze karmienie psa |
| Karta zdrowia | TAK | Gromadzi historię zdrowia psa |
| Szczepienie | TAK | Jest niezależnym wpisem medycznym |
| Lek | TAK | Jest niezależnym elementem dokumentacji medycznej psa |
| Wizyta weterynaryjna | TAK | Reprezentuje zdarzenie medyczne |
| Weterynarz | TAK | Bierze udział w procesie opieki zdrowotnej |
| Pracownik | TAK | Realizuje działania w systemie |
| Rola | TAK | Określa uprawnienia i obowiązki pracownika |
| Płatność | NIE | Reprezentowana jako @embeddable |
| Przedszkole | TAK | Reprezentuje organizację zarządzającą procesami |
