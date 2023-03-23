package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val postBtn: Button = findViewById(R.id.button6)
        postBtn.setOnClickListener {
            Toast.makeText(this, "Post created", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Social::class.java))
        }
    }
}