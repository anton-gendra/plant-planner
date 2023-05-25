package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.model.PlantHomeLocation

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    var plant_name: String? = null
    var plant_type: String? = null
    var bitmapFileName: String? = null
    var location_home: PlantHomeLocation? = null
    var mode: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            plant_name = bundle.getString("plant_name")
            plant_type = bundle.getString("plant_type")
            bitmapFileName = bundle.getString("bitmapFileName")
            location_home = bundle.getSerializable("location_home") as PlantHomeLocation?
            mode = bundle.getString("mode")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        // Inicializa el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        val myList = listOf<SearchItem>(
            SearchItem("Sin resultados"/*, "https://es.wikipedia.org/wiki/Ferocactus#/media/Archivo:Ferocactus_cylindraceus_1.jpg"*/)
        )
        // Crea el adaptador personalizado y asígnalo al RecyclerView
        adapter = SearchAdapter(myList)

        adapter.setOnClickListener(object :
            SearchAdapter.OnClickListener {
            override fun onClick(position: Int, model: SearchItem) {
                val new_plant_type = model.plantName
                val intent = Intent(requireActivity(), PlantAtributtes::class.java)
                if (mode != null) {
                    plant_type = new_plant_type
                    intent.putExtra("EXTRA_MODE", mode)
                    intent.putExtra("EXTRA_NAME", plant_name)
                    intent.putExtra("EXTRA_TYPE", plant_type)
                    intent.putExtra("EXTRA_BITMAP_FILE_NAME", bitmapFileName)
                    intent.putExtra("EXTRA_LOCATION_HOME", location_home)
                } else {
                    intent.putExtra("EXTRA_MODE", "add")
                    intent.putExtra("EXTRA_TYPE", new_plant_type)
                }
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter

        // Configura el RecyclerView con un LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return view
    }

    // si se cierra, volvemos a plantAtributes con los datos que teniamos
    override fun onDestroyView() {
        super.onDestroyView()
        val intent = Intent(requireActivity(), PlantAtributtes::class.java)
        intent.putExtra("EXTRA_MODE", mode)
        intent.putExtra("EXTRA_NAME", plant_name)
        intent.putExtra("EXTRA_TYPE", plant_type)
        intent.putExtra("EXTRA_BITMAP_FILE_NAME", bitmapFileName)
        intent.putExtra("EXTRA_LOCATION_HOME", location_home)
        startActivity(intent)
    }

    fun updateResults(newText: String?) {
        adapter.filter.filter(newText)
    }

    fun updatePlantList(plantList: List<String>) {
        adapter.updatePlantList(plantList)
    }
}