package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.apm.plant_planner.ui.SearchFragment
import com.google.android.material.search.SearchBar

class SearchPlant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plant)

        val searchbar: SearchView = findViewById(R.id.searchView)

        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Aquí puedes actualizar el fragmento en tiempo real mientras el usuario escribe el término de búsqueda
                val fragment = supportFragmentManager.findFragmentById(R.id.frameContainer) as SearchFragment
                fragment.updateResults(newText)
                return true
            }
        })


        supportFragmentManager.commit {
            replace<SearchFragment>(R.id.frameContainer)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }

}