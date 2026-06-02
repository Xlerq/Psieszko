Wymagania biznesowe
Szkoła dla psów zamawia oprogramowanie przeznaczone do zarządzania pobytem zwierząt, organizacją lekcji szkoleniowych oraz obsługą procesów związanych z opieką nad psami. Należy przygotować aplikację serwującą usługi sieciowe w architekturze REST, umożliwiającą realizację podstawowych przypadków użycia zgodnie z wymaganiami przedstawionymi przez Klienta.
Analiza
System umożliwia rejestrację właścicieli psów korzystających z usług szkoły dla psów. Przy pierwszej wizycie pracownik wprowadza podstawowe dane właściciela: imię, nazwisko, numer telefonu oraz adres email. Kartoteka właściciela zawiera również dane kontaktowe i jest powiązana z jednym lub wieloma psami należącymi do danej osoby.

W przypadku pierwszej wizyty psa pracownik rejestruje jego dane w systemie. Dla każdego psa przechowywane są informacje takie jak rasa, dieta, profil behawioralny, cechy zwierzęcia oraz powiązanie z kartoteką właściciela. Każdy pies posiada również profil behawioralny zawierający informacje dotyczące stosunku do innych zwierząt, lęków separacyjnych oraz innych cech istotnych podczas szkolenia. Dodatkowo system umożliwia prowadzenie informacji o diecie psa, uwzględniającej alergie pokarmowe oraz indywidualne zalecenia żywieniowe.

Każdy pracownik przed rozpoczęciem pracy loguje się do systemu przy użyciu indywidualnego konta, które zawiera: imię, nazwisko, numer telefonu oraz adres email. Pracownikowi przypisana jest odpowiednia rola określająca zakres uprawnień w systemie, np. trener, opiekun, weterynarz lub administrator. Administrator systemu zakłada konta pracownicze i nadaje im odpowiednie role oraz uprawnienia.

System umożliwia wprowadzenie rezerwacji pobytu psa. Każda rezerwacja zawiera datę realizacji oraz wskazuje konkretnego psa. Właściciel wynika pośrednio z kartoteki właściciela powiązanej z danym psem. W ramach rezerwacji możliwe jest planowanie lekcji szkoleniowych realizowanych podczas pobytu zwierzęcia.

System umożliwia prowadzenie informacji o aktywnościach i przebiegu zajęć szkoleniowych. W przypadku wystąpienia nieprzewidzianych sytuacji pracownik może zgłosić incydent powiązany z daną lekcją lub pobytem psa.

System prowadzi również rejestr posiłków podawanych psom. Każdy wpis zawiera informacje o ilości podanego pokarmu, procencie zjedzonego posiłku, dacie podania oraz pracowniku odpowiedzialnym za rejestrację wpisu.

Lekcje szkoleniowe odbywają się w odpowiednich strefach nauki. Każda strefa posiada nazwę, maksymalną dopuszczalną liczbę psów oraz przypisane wyposażenie szkoleniowe. Jedna strefa może być wykorzystywana podczas wielu lekcji, jednak każda lekcja odbywa się w jednej konkretnej strefie nauki.

Nad zwierzętami sprawują opiekę weterynarze. System umożliwia prowadzenie kart pacjenta dla każdego psa. Karta pacjenta zawiera historię wizyt weterynaryjnych, rejestr szczepień oraz listę przyjmowanych leków. Każda wizyta weterynaryjna zawiera datę wizyty, opis zaleceń oraz weterynarza odpowiedzialnego za wykonanie wpisu.
