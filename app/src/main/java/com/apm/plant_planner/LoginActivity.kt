package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
        if (!sharedPreferences.contains("eulaAccepted")) {
            sharedPreferences.edit().putBoolean("eulaAccepted", false).apply()
        }

        if (sharedPreferences.contains("username")) {
            sendLoginRequest(sharedPreferences.getString("username", ""), sharedPreferences.getString("password", ""), this)
        }

        supportActionBar?.hide()

        setContentView(R.layout.activity_login)
        checkEula()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    private fun checkEula() {
        val preferences = applicationContext
            .getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val eulaAccepted = preferences.getBoolean("eulaAccepted", false)
        if (!eulaAccepted) {
            val intent = Intent(this, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }
    }

    override fun onResume() {
        if (themeHasChanged(this, "login")) {
            recreate()
        }
        super.onResume()
    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun logIn(view: View) {
        val nameElement: EditText = findViewById(R.id.editTextTextPersonName2)
        val passElement: EditText = findViewById(R.id.editTextTextPassword)

        sendLoginRequest(nameElement.text.toString(), passElement.text.toString(), this)
    }

    fun logInTwitter(view: View) {
        Toast.makeText(applicationContext, R.string.no_implementation, Toast.LENGTH_SHORT).show()
    }

    fun logInGoogle(view: View) {
        Toast.makeText(applicationContext, R.string.no_implementation, Toast.LENGTH_SHORT).show()
    }

    fun enterWithoutLogin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

fun sendLoginRequest(username: String?, password: String?, context: Context) {
    val queue = Volley.newRequestQueue(context)
    val url = "http://10.0.2.2:14000/user/login"

    val body = HashMap<String, String>()
    if (username != null && password != null) {
        body["username"] = username
        body["password"] = password
    }

    val request = object: StringRequest(
        Method.POST, url,
         { response ->
            val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("token", JSONObject(response).getString("access_token"))
                apply()
            }
            if (!sharedPreferences.contains("username")) {
                with(sharedPreferences.edit()) {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }
            }

            Toast.makeText(context, "Login data: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("userid", JSONObject(response).getJSONObject("user").getInt("id")).apply()

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        },
        { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
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
