package com.apm.plant_planner

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
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

    // Get your image
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null) {
                    bitmap = result.data?.extras?.get("data") as Bitmap
                    val plant_image = findViewById<ImageView>(R.id.plant_image)
                    plant_image?.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_atributtes)

        // Obtenemos los datos de entrada
        mode = intent.getStringExtra("EXTRA_MODE")

        if (mode == "edit") {
            Toast.makeText(this, "Edit plant", Toast.LENGTH_LONG).show()
            // change textView title
            findViewById<TextView>(R.id.textView).setText("Editar planta")
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
            findViewById<TextView>(R.id.textView).setText("Añadir planta")
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
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }

        val changePlantTypeBtn: Button = findViewById(R.id.button_search)
        changePlantTypeBtn.setOnClickListener {
            val intent = Intent(this, SearchPlant::class.java)
            intent.putExtra("EXTRA_MODE", mode)
            intent.putExtra("EXTRA_NAME", plantNameEditText.text.toString())
            intent.putExtra("EXTRA_TYPE", plantNameTextView.text.toString())
            intent.putExtra("EXTRA_BITMAP", bitmap)
            intent.putExtra("EXTRA_LOCATION_HOME", locationHomeSpinner.selectedItem as PlantHomeLocation)
            startActivity(intent)
        }

        val discardBtn: Button = findViewById(R.id.discard_btn)
        if (mode == "edit") {
            discardBtn.setText("Eliminar planta")
            discardBtn.setBackgroundColor(0xFFFF0000.toInt())
            discardBtn.setOnClickListener{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Eliminar planta?")
                    .setCancelable(false)
                    .setPositiveButton("Sí") { _, _ ->
                        deletePlant()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                builder.create().show()
            }
        } else {
            discardBtn.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
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

    fun deletePlant() {
        val sharedPreferences = applicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val plantListJson = sharedPreferences.getString("plant_list", "")
        var plantList = Gson().fromJson(plantListJson, Array<Plant>::class.java).toList()

        // if mode edit, remove old plant
        if (mode == "edit") {
            plantList = plantList.filter { it.plant_name != old_plant_name }
        }

        val plantListJsonOutput = Gson().toJson(plantList)
        sharedPreferences.edit().putString("plant_list", plantListJsonOutput).apply()
    }

}