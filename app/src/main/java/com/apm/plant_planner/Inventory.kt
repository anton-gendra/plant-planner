package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Inventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        val settingsBtn: Button = findViewById(R.id.floatingActionButton4)
        settingsBtn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
        val settings2Btn: Button = findViewById(R.id.floatingActionButton5)
        settings2Btn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
    }
}