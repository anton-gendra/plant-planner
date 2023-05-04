package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.utils.*
import org.json.JSONObject
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

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8000/user/login"

        val nameElement: EditText = findViewById(R.id.editTextTextPersonName2)
        val passElement: EditText = findViewById(R.id.editTextTextPassword)

        val body = HashMap<String, String>()
        body["username"] = nameElement.text.toString()
        body["password"] = passElement.text.toString()

        val request = object: StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->

                Toast.makeText(applicationContext, "Login data: ".plus(response.toString()), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
             Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }

            override fun getParams(): MutableMap<String, String>? {
                return body
            }
        }

        queue.add(request)
    }

    fun logInTwitter(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }

    fun logInGoogle(view: View) {
        Toast.makeText(applicationContext, "Not implemented yet.", Toast.LENGTH_SHORT).show()
    }
}
