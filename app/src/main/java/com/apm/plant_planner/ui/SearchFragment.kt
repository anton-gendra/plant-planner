package com.apm.plant_planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
                val plant_type = model.plantName
                Toast.makeText(context, "Seleccionaste $plant_type", Toast.LENGTH_SHORT).show()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}