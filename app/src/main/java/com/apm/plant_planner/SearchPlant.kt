package com.apm.plant_planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.ui.PlantResponse
import com.apm.plant_planner.ui.SearchFragment
import com.apm.plant_planner.ui.SearchItems
import com.google.android.material.search.SearchBar
import com.google.gson.Gson
import java.net.URL

class SearchPlant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plant)

        // obtener lista de especies (no es lo que hay que hacer, pero estoy probando)
        // TODO: podemos utilizar este endpoint para tener una lista de especies que se usa en la app
        // asi si el usuario quiere buscar especies en vez de sacar foto ya tneemos una lista de ellas
        // y con los mismos nombres que los que detectara la camara (no se si me explique, soy angel)

        val queue= Volley.newRequestQueue(this)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val url = "https://my-api.plantnet.org/v2/species?api-key=2b101nCSCBq24oD2NvMubv3gu"

        // Request a string response from the provided URL.
        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                //Toast.makeText(requireContext(), "Response is: ${response.substring(0, 500)}", Toast.LENGTH_SHORT).show()
                println("Response is: $response")
                val gson = Gson()
                val searchItemsList = gson.fromJson(response, Array<PlantResponse>::class.java).toList().map {
                    it.scientificNameWithoutAuthor
                }
                progressBar?.visibility = View.GONE
                val fragment = supportFragmentManager.findFragmentById(R.id.frameContainer) as SearchFragment
                fragment.updatePlantList(searchItemsList)
            },
            Response.ErrorListener { error ->
                progressBar?.visibility = View.GONE
                println(error.toString())
                //Toast.makeText(requireContext(), "Error al realizar la petición", Toast.LENGTH_SHORT).show()
                println("Error al realizar la petición")
            }
        ) {
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(stringRequest)


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