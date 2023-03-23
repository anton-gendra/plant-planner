package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class EmptyInventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_inventory)
    }

    fun searchPlant(view: View) {
        Toast.makeText(applicationContext, "DEBUG: searchPlant.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SearchPlant::class.java))
    }

    fun openCamera(view: View) {
        Toast.makeText(applicationContext, "DEBUG: OpenCamera not implemented yet.", Toast.LENGTH_SHORT).show()
    }
}