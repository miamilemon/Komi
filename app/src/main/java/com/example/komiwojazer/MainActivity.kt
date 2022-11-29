package com.example.komiwojazer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

/*
Do zrobienia(plan projektu):
- Design [X]
- Miasta w spinnerze [ ]
- Macierz wypełniana trójkątem który powoduje, że odległości są takie same z miasta A do B i z B do A [ ]
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