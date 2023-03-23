package com.apm.plant_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchDarkMode: SwitchCompat = findViewById(R.id.theme_switch)
        switchDarkMode.setOnClickListener {
            if (switchDarkMode.isChecked) {
                Toast.makeText(this, "Dark mode activated", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Dark mode deactivated", Toast.LENGTH_SHORT).show()
            }
        }

        val closeSessionBtn: Button = findViewById(R.id.close_session_btn)
        closeSessionBtn.setOnClickListener {
            Toast.makeText(this, "Session closed", Toast.LENGTH_SHORT).show()
        }
    }
}