package com.apm.plant_planner

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.apm.plant_planner.model.Plant
import com.apm.plant_planner.model.PlantHomeLocation


class PlantAtributtes : AppCompatActivity() {

    var plant_name: String? = null
    var plant_type: String? = null
    var bitmap: Bitmap? = null
    var location_home: PlantHomeLocation? = null
    var watering_frequency_weeks: Int? = null
    var location_map_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_atributtes)

        // Obtenemos los datos de entrada
        plant_type = intent.getStringExtra("EXTRA_MESSAGE")
        bitmap = intent.getParcelableExtra("EXTRA_BITMAP") as Bitmap?

        val plantNameTextView = findViewById<TextView>(R.id.plant_name)
        plantNameTextView.setText(plant_type)

        val plantNameEditText = findViewById<EditText>(R.id.editTextPlantName)
        plantNameEditText.setText(plant_type)

        if (bitmap != null) {
            val plantImageView = findViewById<ImageView>(R.id.plant_image)
            plantImageView.setImageBitmap(bitmap)
        }

        // datos del Spinner para location_home
        val locationHomeSpinner = findViewById<Spinner>(R.id.spinnerLocationHome)
        locationHomeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            PlantHomeLocation.values()
        )

        // Botones de la pantalla
        val changepicBtn: Button = findViewById(R.id.button)
        changepicBtn.setOnClickListener {
            Toast.makeText(this, "Change plant picture", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Camera::class.java))
        }
        val changeMapLocationBtn: Button = findViewById(R.id.button2)
        changeMapLocationBtn.setOnClickListener {
            startActivity(Intent(this, MapPicker::class.java))
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
            val plant = Plant(plant_name!!, plant_type!!, bitmap, location_home, watering_frequency_weeks, location_map_name)
            Toast.makeText(this, "Planta creada correctamente", Toast.LENGTH_SHORT).show()
            println("Planta:")
            println(plant)
        }
    }

}