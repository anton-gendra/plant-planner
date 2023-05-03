package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.apm.plant_planner.model.Plant
import com.apm.plant_planner.utils.*
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "login")
        super.onCreate(savedInstanceState)

        val sharedPreferences = applicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("plant_list")) {
            val plantList = emptyList<Plant>()
            val plantListJson = Gson().toJson(plantList)
            sharedPreferences.edit().putString("plant_list", plantListJson).apply()
        }

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
        println("DEBUG: Starting MainActivity.")
        startActivity(intent)
    }

    fun logInTwitter(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }

    fun logInGoogle(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }
}