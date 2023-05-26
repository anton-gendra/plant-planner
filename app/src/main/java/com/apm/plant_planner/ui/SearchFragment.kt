package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.model.PlantHomeLocation

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    var plantname: String? = null
    var planttype: String? = null
    var bitmapFileName: String? = null
    var locationhome: PlantHomeLocation? = null
    var mode: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            plantname = bundle.getString("plant_name")
            planttype = bundle.getString("plant_type")
            bitmapFileName = bundle.getString("bitmapFileName")
            locationhome = bundle.getSerializable("location_home") as PlantHomeLocation?
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
        val myList = listOf(
            SearchItem("Sin resultados", "https://es.wikipedia.org/wiki/Ferocactus#/media/Archivo:Ferocactus_cylindraceus_1.jpg")
        )
        // Crea el adaptador personalizado y asígnalo al RecyclerView
        adapter = SearchAdapter(myList)

        adapter.setOnClickListener(object :
            SearchAdapter.OnClickListener {
            override fun onClick(position: Int, model: SearchItem) {
                val newplanttype = model.plantName
                val intent = Intent(requireActivity(), PlantAtributtes::class.java)
                if (mode != null) {
                    planttype = newplanttype
                    intent.putExtra("EXTRA_MODE", mode)
                    intent.putExtra("EXTRA_NAME", plantname)
                    intent.putExtra("EXTRA_TYPE", planttype)
                    intent.putExtra("EXTRA_BITMAP_FILE_NAME", bitmapFileName)
                    intent.putExtra("EXTRA_LOCATION_HOME", locationhome)
                } else {
                    intent.putExtra("EXTRA_MODE", "add")
                    intent.putExtra("EXTRA_TYPE", newplanttype)
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

    fun updateResults(newText: String?) {
        adapter.filter.filter(newText)
    }

    fun updatePlantList(plantList: List<String>) {
        adapter.updatePlantList(plantList)
    }
}