package com.apm.plant_planner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.utils.setNewTheme
import com.apm.plant_planner.utils.themeHasChanged
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "register")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerBtn: Button = findViewById(R.id.submit_register)
        val userNameEl: EditText = findViewById(R.id.user_name_edit)
        val passwordEl: EditText = findViewById(R.id.password_edit)
        val repeatPasswordEl: EditText = findViewById(R.id.repeat_password_edit)

        registerBtn.setOnClickListener {
            val pass = passwordEl.text
            val userName = userNameEl.text
            val repeatedPass = repeatPasswordEl.text

            if (userName != null && pass != null && pass.toString() == repeatedPass.toString()) {
                val queue = Volley.newRequestQueue(this)
                val url = "http://10.0.2.2:8000/user/register"

                val body = JSONObject()
                body.put("name", userName)
                body.put("password", pass)


                val request = object: JsonObjectRequest(
                    Method.POST, url, body,
                    { response ->

                        Toast.makeText(this, "Register data: ".plus(response.toString()), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    },
                    { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }
                ) {}

                queue.add(request)

                startActivity(Intent(this, LoginActivity::class.java))

            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
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