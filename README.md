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
7. Przycisk zapisu powoduje wykonanie zrzutu ekranu, który zapisywany jest w galerii.
