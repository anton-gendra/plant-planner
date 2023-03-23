package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Social : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        val addPostBtn: FloatingActionButton = findViewById(R.id.floatingActionButton8)
        addPostBtn.setOnClickListener {
            Toast.makeText(this, "Create new post", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Post::class.java))
        }
    }
}