package com.example.komiwojazer

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


/*
Do zrobienia(plan projektu):
- Design [X]
- Miasta w spinnerze [X]
- Macierz wypełniana trójkątem który powoduje, że odległości są takie same z miasta A do B i z B do A [X]
- Wykrywanie zaznaczonych elementów na spinnerze [X]
- Pokazywanie odległości przy zaznaczeniu miast [X]
- Gdy są dwa te same miasta to nie można zmienić odległości [X]
- Zmiana odległości według zasad działania [X]
- Zrzut ekranu zapisywany do galerii [ ]
- Algorytm, którego funkcjonalność działa [ ]
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

        spinnerA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                editDystans.setText(miasta[spinnerA.selectedItemId.toInt()][spinnerB.selectedItemId.toInt()].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        spinnerB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                editDystans.setText(miasta[spinnerA.selectedItemId.toInt()][spinnerB.selectedItemId.toInt()].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        zmiana.setOnClickListener {
            if(!editDystans.text.isNullOrEmpty()){
                if(spinnerA.selectedItemId.toInt() == spinnerB.selectedItemId.toInt()){

                }else{
                    miasta[spinnerA.selectedItemId.toInt()][spinnerB.selectedItemId.toInt()] = editDystans.getText().toString().toInt()
                    miasta[spinnerB.selectedItemId.toInt()][spinnerA.selectedItemId.toInt()] = editDystans.getText().toString().toInt()
                }
            }
        }

        trasa.setOnClickListener(View.OnClickListener {
            var screenshot: Bitmap? = null
            val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            screenshot = Bitmap.createBitmap(MyLayoutView.drawingCache)
            val canvas = Canvas(screenshot)
            MyLayoutView.draw(canvas)
            val plik: FileOutputStream = FileOutputStream(file.absolutePath+"/komiwojazer.jpg")
            screenshot.compress(Bitmap.CompressFormat.JPEG, 100, plik)
            plik.flush()
            plik.close()
        })


    }
}