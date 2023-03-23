package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    fun calendar(view: View) {
        Toast.makeText(applicationContext, "DEBUG: Calendar.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Calendar::class.java))
    }

    fun social(view: View) {
        Toast.makeText(applicationContext, "DEBUG: Social.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Social::class.java))
    }

    fun profile(view: View) {
        Toast.makeText(applicationContext, "DEBUG: Profile.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Profile::class.java))
    }
}