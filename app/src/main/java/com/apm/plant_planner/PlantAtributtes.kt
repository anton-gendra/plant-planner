package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.apm.plant_planner.model.Plant
import com.apm.plant_planner.model.PlantHomeLocation
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File


class PlantAtributtes : AppCompatActivity() {

    var mode: String? = null
    var old_plant_name: String? = null
    var bitmap: Bitmap? = null

    var plant_name: String? = null
    var plant_type: String? = null
    var bitmapFileName: String? = null
    var location_home: PlantHomeLocation? = null
    var watering_frequency_weeks: Int? = null

    fun savePlantList(plant: Plant): Boolean {
        val sharedPreferences = applicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val plantListJson = sharedPreferences.getString("plant_list", "")
        var plantList = Gson().fromJson(plantListJson, Array<Plant>::class.java).toList()

        // check if plant name already exists
        for (p in plantList) {
            if (p.plant_name == plant.plant_name && (mode == "new" || p.plant_name != old_plant_name)) {
                return false
            }
        }

        // if mode edit, remove old plant
        if (mode == "edit") {
            plantList = plantList.filter { it.plant_name != old_plant_name }
        }

        plantList += plant
        val plantListJsonOutput = Gson().toJson(plantList)
        sharedPreferences.edit().putString("plant_list", plantListJsonOutput).apply()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_atributtes)

        // Obtenemos los datos de entrada
        mode = intent.getStringExtra("EXTRA_MODE")

        if (mode == "edit") {
            Toast.makeText(this, "Edit plant", Toast.LENGTH_LONG).show()
            // change textView title
            findViewById<TextView>(R.id.plant_name).setText("Editar planta")
            findViewById<Button>(R.id.button4).setText("Guardar cambios")
            old_plant_name = intent.getStringExtra("EXTRA_NAME")
            plant_type = intent.getStringExtra("EXTRA_TYPE")
            bitmapFileName = intent.getStringExtra("EXTRA_BITMAP_FILE_NAME")
            location_home = intent.getStringExtra("EXTRA_LOCATION_HOME") as PlantHomeLocation?

            if (bitmapFileName != null) {
                val file = File(applicationContext.filesDir, bitmapFileName)
                bitmap = BitmapFactory.decodeFile(file.absolutePath)
            }
        } else {
            findViewById<TextView>(R.id.plant_name).setText("Añadir planta")
            findViewById<Button>(R.id.button4).setText("Añadir planta")
            bitmap = intent.getParcelableExtra("EXTRA_BITMAP") as Bitmap?
        }

        plant_name = intent.getStringExtra("EXTRA_NAME")
        plant_type = intent.getStringExtra("EXTRA_TYPE")

        val plantNameTextView = findViewById<TextView>(R.id.plant_name)
        plantNameTextView.setText(plant_type)

        val plantNameEditText = findViewById<EditText>(R.id.editTextPlantName)
        plantNameEditText.setText(plant_name)

        if (bitmap != null) {
            val plantImageView = findViewById<ImageView>(R.id.plant_image)
            plantImageView.setImageBitmap(bitmap)
        }

        // datos del Spinner para location_home
        val locationHomeSpinner = findViewById<Spinner>(R.id.spinnerLocationHome)
        if (location_home != null) {
            locationHomeSpinner.setSelection(location_home!!.ordinal)
        }
        locationHomeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            PlantHomeLocation.values()
        )

        // Botones de la pantalla
        val changepicBtn: Button = findViewById(R.id.button)
        changepicBtn.setOnClickListener {
            Toast.makeText(this, "Change plant picture", Toast.LENGTH_LONG).show()
            //startActivity(Intent(this, Camera::class.java))
        }
        val discardBtn: Button = findViewById(R.id.discard_btn)
        discardBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        val addPlantBtn: Button = findViewById(R.id.button4)
        addPlantBtn.setOnClickListener {
            plant_name = plantNameEditText.text.toString()
            location_home = locationHomeSpinner.selectedItem as PlantHomeLocation
            createPlant()
        }
    }

    fun createPlant() {
        if (plant_type == null || plant_name == null || location_home == null) {
            Toast.makeText(this, "Por favor, rellena todos los campos marcador con *", Toast.LENGTH_SHORT).show()
        } else if (plant_name!! == "") {
            Toast.makeText(this, "Por favor, indique un nombre para su planta", Toast.LENGTH_SHORT).show()
        } else {
            var bitmapFileName: String? = null
            val outputStream = ByteArrayOutputStream()
            if (bitmap != null) {
                bitmapFileName = "$plant_name.png"
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                val file = File(this.filesDir, bitmapFileName)
                file.writeBytes(byteArray)
            }


            val plant = Plant(plant_name!!, plant_type!!, bitmapFileName, location_home, watering_frequency_weeks)
            if (savePlantList(plant)) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Plant name already exists", Toast.LENGTH_LONG).show()
            }
        }
    }

}