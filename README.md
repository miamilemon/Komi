# Problem komiwojażera
### Problem polega na znalezieniu najkrótszej trasy z punktu X do punktu Y, obowiązkowo odwiedzając po drodze każdy punkt. Ponadto żaden z punktów nie może być odwiedzony dwukrotnie.
#### Nie wydaje się to być skomplikowane, ale jest. Możliwość wszystkich kombinacji przy 16 punktach to 16 silnia co daje 20 922 789 888 000 możliwości.
#### W celu rozwiązania tego problemu od dawna wymyślane są najróżniejsze algorytmy. Jednak żaden z nich jak dotąd nie zapewnił najbardziej możliwie optymalnego rozwiązania. W zależności od ilości punktów, radzą sobie one w lepszy lub gorszy sposób.
### Mój program stworzony został stworzony w celu możliwości wizualizacji problemu, przy użyciu jednego z algorytmów próbujących ten problem rozwiązać.
#### Główne założenia programu:
1. Program operuje na 16 punktach (dalej zwanych jako miasta).
2. Odległośc między każdym miastem jest generowana losowo.
3. Odległość miasta do samego siebie wynosi 0.
4. Odległość z miasta A do miasta B jest taka sama jak z miasta B do miasta A.
5. Użytkownik może zmienić odległość między każdym miastem. W tym przypadku działa zasada z punktu 4. czyli w przypadku zmiany odległości z miasta A do miasta B, program samodzielnie zmienia również odległośc z miasta B do miasta A, na podaną przez użytkownika.
6. Po naciśnięciu przycisku "TRASA" program oblicza trasę z punktu X do punktu Y, które wybiera użytkownik, a następnia ją wyświetla.
7. Po obliczeniu trasy i wyświetleniu jej na ekranie, program robi zrzut ekranu i zapisuje go w galerii.

## Podstawowa instrukcja obsługi
### Miasto A, miasto B, punkt X, punkt Y oznacza dowolny punkt/miasto, pojęcia punkt/miasto używane są zamiennie.
#### Program generuje losowo odległości między miastami, więc użytkownik nie musi ich określać. Użytkownik ma możliwość zmiany tych odległości, poprzez wybranie obu punktów z pól rozwijanych i wpisaniu wartości w polu edycyjnym obok napisu odległość. Wszystko należy potwierdzić przyciskiem z widocznym napisem ZMIEŃ.
#### W celu obliczenia trasy z punktu X do punktu Y, użytkownik wybiera miasta z list rozwijanych, gdzie pole na górze oznacza punkt początkowy, a pole dolne oznacza punkt końcowy. W celu uruchomienia obliczeń użytkownik musi nacisnąć przycisk z napisem TRASA. Po naciśnięciu przycisku, program oblicza trasę, wyświetla efekt obliczeń i zapisuje zrzut ekranu w galerii. W razie wyświetlenia komunikatu o zezwoleniu aplikacji na dostęp do poszczególnych elementów, należy naciskać przycisk z napisem ZEZWÓL.
#### Dodatkowe informacje dla użytkownika:
1. Zmiana odległości z miasta A do B, zmienia odległość z miasta B do miasta A na taką samą wartość.
2. Nie można zmienić odległości z miasta A do miasta A.
3. Odległości między miastami mogą być tylko dodatnie.
