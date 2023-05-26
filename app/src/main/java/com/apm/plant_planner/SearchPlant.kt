package com.apm.plant_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.commit
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.model.PlantHomeLocation
import com.apm.plant_planner.ui.PlantResponse
import com.apm.plant_planner.ui.SearchFragment
import com.apm.plant_planner.utils.setNewTheme
import com.google.gson.Gson

class SearchPlant : AppCompatActivity() {

    // atributos para recibir y devolver de plant attributes
    var plantname: String? = null
    var planttype: String? = null
    var bitmapFileName: String? = null
    var locationhome: PlantHomeLocation? = null
    var mode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "searchPlant")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plant)

        // obtener lista de especies
        // TODO: podemos utilizar este endpoint para tener una lista de especies que se usa en la app
        // asi si el usuario quiere buscar especies en vez de sacar foto tenemos una lista de ellas

        val queue= Volley.newRequestQueue(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val frameContainer = findViewById<View>(R.id.frameContainer)
        val url = "https://my-api.plantnet.org/v2/species?api-key=2b101nCSCBq24oD2NvMubv3gu"

        // Request a string response from the provided URL.
        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                println("Response is: $response")
                val gson = Gson()
                val searchItemsList = gson.fromJson(response, Array<PlantResponse>::class.java).toList().map {
                    it.scientificNameWithoutAuthor
                }
                progressBar?.visibility = View.GONE
                frameContainer?.visibility = View.VISIBLE
                val fragment = supportFragmentManager.findFragmentById(R.id.frameContainer) as SearchFragment
                fragment.updatePlantList(searchItemsList)
            },
            Response.ErrorListener { error ->
                progressBar?.visibility = View.GONE
                frameContainer?.visibility = View.VISIBLE
                println(error.toString())
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

        mode = intent.getStringExtra("EXTRA_MODE")
        plantname = intent.getStringExtra("EXTRA_NAME")
        planttype = intent.getStringExtra("EXTRA_TYPE")
        bitmapFileName = intent.getStringExtra("EXTRA_BITMAP_FILE_NAME")
        locationhome = intent.getStringExtra("EXTRA_LOCATION_HOME") as PlantHomeLocation?

        val fragment = SearchFragment()

        val bundle = Bundle().apply {
            putString("plant_name", plantname)
            putString("plant_type", planttype)
            putString("bitmapFileName", bitmapFileName)
            putSerializable("location_home", locationhome)
            putString("mode", mode)
        }

        fragment.arguments = bundle

        supportFragmentManager.commit {
            replace(R.id.frameContainer, fragment)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }

    }

}