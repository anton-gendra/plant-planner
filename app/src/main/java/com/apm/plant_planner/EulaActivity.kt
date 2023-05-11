package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spanned
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream


class EulaActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = applicationContext
            .getSharedPreferences("com.apm.plant_planner", MODE_PRIVATE)
        setContentView(R.layout.activity_eula)
        initializeUI()
    }

    private fun initializeUI() {
        val eulacancel: Button = findViewById(R.id.eula_cancel)
        val eulaconfirm: Button = findViewById(R.id.eula_confirm)
        eulacancel.setOnClickListener { cancelEULA() }
        eulaconfirm.setOnClickListener {
            confirmEULA()
        }
        setEula()
    }

    private fun setEula() {
        val eula = intent.extras!!.getInt("eula")
        val inputStream: InputStream = resources.openRawResource(eula)
        val bytes = ByteArray(inputStream.available())
        val eulacontent: TextView = findViewById(R.id.eula_content)
        inputStream.read(bytes)
        val htmlAsSpanned: Spanned =
            HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY)
        eulacontent.text = htmlAsSpanned
    }

    private fun cancelEULA() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "You must accept the EULA to continue",
            Snackbar.LENGTH_LONG
        ).show()
    }

    // NOTE: Here you would call your api to save the status
    private fun confirmEULA() {
        preferences.edit().putBoolean("eulaAccepted", true).apply()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}