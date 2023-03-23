package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MapPicker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_picker)
        val locationBtn: Button = findViewById(R.id.toolbar2)
        locationBtn.setOnClickListener {
            Toast.makeText(this, "Location button to go back to your position", Toast.LENGTH_LONG).show()
        }
        val addBtn: Button = findViewById(R.id.floatingActionButton)
        addBtn.setOnClickListener {
            Toast.makeText(this, "Button to add a new location", Toast.LENGTH_LONG).show()
        }
        val backBtn: Button = findViewById(R.id.floatingActionButton2)
        backBtn.setOnClickListener {
            Toast.makeText(this, "Back button to plant attributes", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
    }
}