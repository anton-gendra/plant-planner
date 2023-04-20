package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

import com.apm.plant_planner.utils.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "")
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

        val lightBtn: RadioButton = findViewById(R.id.light_theme)
        val light2Btn: RadioButton = findViewById(R.id.light_theme_2)
        val darkBtn: RadioButton = findViewById(R.id.dark_theme)

        val pref: SharedPreferences = getSharedPreferences("plant-planner-style", Context.MODE_PRIVATE)
        
        when (pref.getInt("theme", -1)) {
            R.style.Theme_Plantplanner_light_1 or -1 -> lightBtn.isChecked = true
            R.style.Theme_Plantplanner_light_2 -> light2Btn.isChecked = true
            R.style.Theme_Plantplanner_dark -> darkBtn.isChecked = true
        }

        lightBtn.setOnClickListener {
            Toast.makeText(this, "Light 1 theme selected", Toast.LENGTH_SHORT).show()
            light2Btn.isChecked = false
            darkBtn.isChecked = false
            with (pref.edit()) {
                putInt("theme", R.style.Theme_Plantplanner_light_1)
                setDefaultAdoptedSettings(this)
            }
            recreate()
        }

        light2Btn.setOnClickListener {
            Toast.makeText(this, "Light 2 theme selected", Toast.LENGTH_SHORT).show()
            lightBtn.isChecked = false
            darkBtn.isChecked = false
            with (pref.edit()) {
                putInt("theme", R.style.Theme_Plantplanner_light_2)
                setDefaultAdoptedSettings(this)
            }
            recreate()
        }

        darkBtn.setOnClickListener {
            Toast.makeText(this, "Dark theme selected", Toast.LENGTH_SHORT).show()
            lightBtn.isChecked = false
            light2Btn.isChecked = false
            with (pref.edit()) {
                putInt("theme", R.style.Theme_Plantplanner_dark)
                setDefaultAdoptedSettings(this)
            }
            recreate()
        }
    }

    private fun setDefaultAdoptedSettings(editor: Editor) {
        editor.putBoolean("main_theme_adopted", false)
        editor.putBoolean("login_theme_adopted", false)
        editor.putBoolean("register_theme_adopted", false)
        editor.commit()
    }

}