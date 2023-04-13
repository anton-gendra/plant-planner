package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.apm.plant_planner.ui.SearchFragment

class SearchPlant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plant)

        supportFragmentManager.commit {
            replace<SearchFragment>(R.id.frameContainer)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }

}