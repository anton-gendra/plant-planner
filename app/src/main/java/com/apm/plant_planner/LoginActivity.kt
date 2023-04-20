package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.apm.plant_planner.utils.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "login")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onResume() {
        if (themeHasChanged(this, "login")) {
            recreate()
        }
        super.onResume()
    }

    fun register(view: View) {
        Toast.makeText(applicationContext, "DEBUG: Register.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun logIn(view: View) {
        // Actualmente se usar un val y no se mete directamente porque luego habra mas datos
        // de inicio de sesion
        Toast.makeText(applicationContext, "Successfully log in.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun logInTwitter(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }

    fun logInGoogle(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }
}