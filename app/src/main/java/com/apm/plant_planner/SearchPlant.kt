package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SearchPlant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plant)
        val plantcardBtn: Button = findViewById(R.id.card_view)
        plantcardBtn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
        val plantcard2Btn: Button = findViewById(R.id.card_view2)
        plantcard2Btn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
        val plantcard3Btn: Button = findViewById(R.id.card_view3)
        plantcard3Btn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
        val plantcard4Btn: Button = findViewById(R.id.card_view4)
        plantcard4Btn.setOnClickListener {
            Toast.makeText(this, "Select the plant", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlantAtributtes::class.java))
        }
    }

}