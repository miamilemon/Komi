package com.example.komiwojazer

import android.app.PendingIntent.getActivity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import java.io.File
import java.io.FileOutputStream
import java.util.Random
import android.app.Activity
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import java.net.URI


/*
Do zrobienia(plan projektu):
- Design [X]
- Miasta w spinnerze [X]
- Macierz wypełniana trójkątem który powoduje, że odległości są takie same z miasta A do B i z B do A [X]
- Wykrywanie zaznaczonych elementów na spinnerze [X]
- Pokazywanie odległości przy zaznaczeniu miast [X]
- Gdy są dwa te same miasta to nie można zmienić odległości [X]
- Zmiana odległości według zasad działania [X]
- Zrzut ekranu zapisywany do galerii [X]
- Algorytm, którego funkcjonalność działa [X]
 */


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinnerA : Spinner = findViewById(R.id.spinnerA)
        val spinnerB : Spinner = findViewById(R.id.spinnerB)
        val editDystans : EditText = findViewById(R.id.editTextDystans)
        val zmiana : Button = findViewById(R.id.buttonZmiana)
        val trasa : Button = findViewById(R.id.buttonTrasa)
        val textWynik: TextView = findViewById(R.id.textWynikTrasa)
        val MyLayoutView : View = findViewById(R.id.MyLayout)
        //Macierz odległości między miastami wypełniona 0
        val miasta = List<MutableList<Int>>(8){
            MutableList<Int>(8){0}
        }
        //Pętla wypełniająca macierz z odległościami, losowymi elementami
        //Pętla w postaci trójkąta, która powoduje że między tymi samymi punktami odległość pozostaje 0
        for(i in 0..7){
            for(j in i+1..7){
                //Generowanie losowych liczb w przedziale 1,1089
                Random().nextInt(1089).let{
                    //Przypisywanie wygenerowanej losowo liczby do tablicy
                    //Dla odległości między A i B daje taką samą wartość jak między B i A
                    miasta[i][j]=it
                    miasta[j][i]=it
                }
            }
        }
        //To coś zapełnia spinnera elementami ze strings.xml
        ArrayAdapter.createFromResource(
            this,
            R.array.miasta,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerA.adapter = adapter
            spinnerB.adapter = adapter
        }

        //Funkcja sprawdzająca wybrane elementy ze spinnera i wypisujące odglełość z macierzy między tymi elementami do pola tekstowego
        fun sprawdzOdleglosc(){
            editDystans.setText(miasta[spinnerA.selectedItemId.toInt()][spinnerB.selectedItemId.toInt()].toString())
        }

        //W przypadku zmiany zaznaczonego elementu w spinnerze wywołuje funkcje sprawdzającą
        spinnerA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Wywołanie funkcji która sprawdza odległość
                sprawdzOdleglosc()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        //W przypadku zmiany zaznaczonego elementu w spinnerze wywołuje funkcje sprawdzającą
        spinnerB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Wywołanie funkcji która sprawdza odległość
                sprawdzOdleglosc()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        zmiana.setOnClickListener {
            if(!editDystans.text.isNullOrEmpty()){
                if(spinnerA.selectedItemId.toInt() == spinnerB.selectedItemId.toInt()){

                }else{
                    if(editDystans.text.toString().toInt()==0){

                    }else{
                        miasta[spinnerA.selectedItemId.toInt()][spinnerB.selectedItemId.toInt()] = editDystans.getText().toString().toInt()
                        miasta[spinnerB.selectedItemId.toInt()][spinnerA.selectedItemId.toInt()] = editDystans.getText().toString().toInt()
                    }
                }
            }
        }

        trasa.setOnClickListener(View.OnClickListener {
            //Czyszcenie tekstu w razie gdyby wcześniej już coś tam było np. poprzedni wynik działania programu
            textWynik.setText("")

            // Algorytm działa na wyżej stworzonej macierzy, polega na heurystyce znajdywaniu najbliższego sąsiada

            val size = 8
            // Tablica przechowująca odwiedzone miasta
            val visited = BooleanArray(size)

            // Tablica przechowująca najkrótszą ścieżkę
            val path = IntArray(size)

            // Miasto startowe
            var currentCity = 0

            // Startowe miasto zostaje dodane do tablicy odwiedzonych miast
            visited[currentCity] = true

            for (i in 0 until size - 1) {
                // Zmienna przechowująca najkrótszy dystans, na początku jest to nieskończoność (w tym przypadku maksymalna wartość inta)
                var min = Int.MAX_VALUE

                // Zmienna przechowująca indeks miasta z najkrótszym dystansem
                var cityIndex = 0

                // Szukanie miasta z najkrótszym dystansem
                for (j in 0 until size) {
                    if (!visited[j] && miasta[currentCity][j] < min) {
                        min = miasta[currentCity][j]
                        cityIndex = j
                    }
                }

                // Dodanie miasta do ścieżki i oznaczenie jako odwiedzone
                path[i] = cityIndex
                visited[cityIndex] = true

                // Zmiana miasta na którym pracuje algorytm
                currentCity = cityIndex
            }

            // Dodanie ostatniego miasta do ścieżki
            path[size - 1] = 0

            //Wypisanie najkrótszej ścieżki
            for (i in 0 until size) {
                textWynik.append("${path[i]} -> ")
            }

            // Wypisanie całkowitej odległości od punkt pierwszego do ostatniego
            var cost = 0
            for (i in 0 until size - 1) {
                cost += miasta[path[i]][path[i + 1]]
            }
            textWynik.append("\nCałkowita odległość: $cost")

            //Zrzut ekranu do galerii
            //Uzyskanie widoku view
            val activity = this
            val view = activity.window.decorView.rootView

            //Bitmapa na podstawie wartości uzyskanych z naszego view
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

            //Płótno/canvas które pozwoli nam na stworzenie obrazka
            val canvas = Canvas(bitmap)

            //Wypełnienie canvasu widokiem view
            view.draw(canvas)

            //Stworzenie ścieżki dla pliku
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "screenshot.png"
            //Stworzenie pliku
            val file = File(filePath)
            //Zapis pliku
            try {
                //Stream do zapisu pliku
                val stream = FileOutputStream(file)

                //Kompresja bitmapy do PNG
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

                //Zamknięcie streama zapisu żeby nie zajmowało nam miejsca w pamięci
                stream.flush()
                stream.close()
            } catch (e: Exception) {
                //Wypisanie błędu w konsoli w przypadku niepowodzenia
                e.printStackTrace()
            }
        })
    }
}