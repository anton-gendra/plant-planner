package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.SearchPlant
import com.apm.plant_planner.model.Plant
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {

    var ListPlant: ArrayList<Plant> = ArrayList<Plant>()

    fun addPlant(plant: Plant) {
        ListPlant.add(plant)
    }

    fun getPlantByName(plant_name: String): Plant? {
        for (plant in ListPlant) {
            if (plant.plant_name == plant_name) {
                return plant
            }
        }
        return null
    }

    fun deletePlant(plant_name: String) {
        for (plant in ListPlant) {
            if (plant.plant_name == plant_name) {
                ListPlant.remove(plant)
                return
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ListPlant.isEmpty()) {
            // si el inventario esta vacío, mostramos el fragment de EmptyInventory
            println("Inventario vacío, mostrando fragmento de inventario vacío")
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, EmptyInventoryFragment())
                .commit()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        val Button: FloatingActionButton = view.findViewById(R.id.floatingActionButton4)
        Button.setOnClickListener {
            val intent = Intent(requireActivity(), PlantAtributtes::class.java)
            startActivity(intent)
        }
        val Button2: FloatingActionButton = view.findViewById(R.id.floatingActionButton5)
        Button2.setOnClickListener {
            val intent = Intent(requireActivity(), PlantAtributtes::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }
}