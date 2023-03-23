package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val settingsBtn: Button = findViewById(R.id.access_settings_btn)
        settingsBtn.setOnClickListener {
            Toast.makeText(this, "Accessing settings", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Settings::class.java))
        }
    }
}