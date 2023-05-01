package com.apm.plant_planner

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class PlantAtributtes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_atributtes)

        val plant_type = intent.getStringExtra("EXTRA_MESSAGE")
        val bitmap = intent.getParcelableExtra("EXTRA_BITMAP") as Bitmap?

        val plantNameTextView = findViewById<TextView>(R.id.plant_name)
        plantNameTextView.setText(plant_type)

        val plantNameEditText = findViewById<EditText>(R.id.editTextPlantName)
        plantNameEditText.setText(plant_type)

        val plantImageView = findViewById<ImageView>(R.id.plant_image)
        plantImageView.setImageBitmap(bitmap)

        val changepicBtn: Button = findViewById(R.id.button)
        changepicBtn.setOnClickListener {
            Toast.makeText(this, "Change plant picture", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Camera::class.java))
        }
        val changeMapLocationBtn: Button = findViewById(R.id.button2)
        changeMapLocationBtn.setOnClickListener {
            Toast.makeText(this, "Change map location", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MapPicker::class.java))
        }
        val discardBtn: Button = findViewById(R.id.discard_btn)
        discardBtn.setOnClickListener {
            Toast.makeText(this, "Discard plant attributes", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Inventory::class.java))
        }
        val addPlantBtn: Button = findViewById(R.id.button4)
        addPlantBtn.setOnClickListener {
            Toast.makeText(this, "Add the new plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Inventory::class.java))
        }
    }

}