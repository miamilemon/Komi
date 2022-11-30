package com.example.komiwojazer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.util.*
import kotlin.random.Random.Default.nextInt

/*
Do zrobienia(plan projektu):
- Design [X]
- Miasta w spinnerze [X]
- Macierz wypełniana trójkątem który powoduje, że odległości są takie same z miasta A do B i z B do A [X]
- Wykrywanie zaznaczonych elementów na spinnerze [ ]
- Pokazywanie odległości przy zaznaczeniu miast [ ]
- Gdy są dwa te same miasta to nie można zmienić odległości [ ]
- Zmiana odległości według zasad działania [ ]
- Zrzut ekranu zapisywany do galerii [ ]
- Algorytm, którego funkcjonalność działa [ ]
 */


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinnerA : Spinner = findViewById(R.id.spinnerA)
        val spinnerB : Spinner = findViewById(R.id.spinnerB)
        //Macierz odległości między miastami wypełniona 0
        val miasta = List<MutableList<Int>>(16){
            MutableList<Int>(16){0}
        }
        //Pętla wypełniająca macierz z odległościami, losowymi elementami
        //Pętla w postaci trójkąta, która powoduje że między tymi samymi punktami odległość pozostaje 0
        for(i in 0..15){
            for(j in i+1..15){
                //Generowanie losowych liczb w przedziale 1,1089
                kotlin.random.Random.nextInt(1,1089).let{
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



    }
}