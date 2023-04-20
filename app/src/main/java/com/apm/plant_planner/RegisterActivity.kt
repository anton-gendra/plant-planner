package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.apm.plant_planner.utils.setNewTheme
import com.apm.plant_planner.utils.themeHasChanged

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "register")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onResume() {
        if (themeHasChanged(this, "register")) {
            recreate()
        }
        super.onResume()
    }

    fun register(view: View) {
        Toast.makeText(applicationContext, "Successfully registered.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}