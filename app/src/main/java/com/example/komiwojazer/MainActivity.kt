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
            textWynik.setText("")

            // This algorithm uses the nearest neighbor heuristic to solve the Traveling Salesman Problem using random matrix

            val size = 8
            // Create array to store the visited cities
            val visited = BooleanArray(size)

            // Create array to store the shortest path
            val path = IntArray(size)

            // Assign starting city
            var currentCity = 0

            // Add starting city to the visited list
            visited[currentCity] = true

            // Start the loop
            for (i in 0 until size - 1) {
                // Create a variable to hold the shortest distance
                var min = Int.MAX_VALUE

                // Create a variable to store the index of the city with the shortest distance
                var cityIndex = 0

                // Find the city with the shortest distance
                for (j in 0 until size) {
                    if (!visited[j] && miasta[currentCity][j] < min) {
                        min = miasta[currentCity][j]
                        cityIndex = j
                    }
                }

                // Add the city to the path and mark it as visited
                path[i] = cityIndex
                visited[cityIndex] = true

                // Update the current city
                currentCity = cityIndex
            }

            // Add the last city to the path
            path[size - 1] = 0

            // Print the shortest path
            //println("The shortest path is:")
            for (i in 0 until size) {
                textWynik.append("${path[i]} -> ")
            }

            // Calculate and print the cost of the path
            var cost = 0
            for (i in 0 until size - 1) {
                cost += miasta[path[i]][path[i + 1]]
            }
            textWynik.append("\nCałkowita odległość: $cost")

            val activity = this
            //Get the root view of the activity layout
            val view = activity.window.decorView.rootView

            //Create a bitmap with the same size as the view
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

            //Create a canvas with the bitmap for drawing on
            val canvas = Canvas(bitmap)

            //Draw the view on the canvas
            view.draw(canvas)

            //Create a file to save the bitmap
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "screenshot.png"
            val file = File(filePath)

            try {
                //Create a file output stream
                val stream = FileOutputStream(file)

                //Compress the bitmap and save it to the file output stream
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

                //Flush and close the output stream
                stream.flush()
                stream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        })


    }
}