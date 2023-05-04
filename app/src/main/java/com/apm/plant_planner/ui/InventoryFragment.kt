package com.apm.plant_planner.ui

import android.content.Context
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
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {

    var plantList = emptyList<Plant>()
    //val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        println("DEBUG: onCreate InventoryFragment")
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val plantListJson = sharedPreferences.getString("plant_list", "")
        plantList = Gson().fromJson(plantListJson, Array<Plant>::class.java).toList()

        if (plantList.isEmpty()) {
            // si el inventario esta vacío, mostramos el fragment de EmptyInventory
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, EmptyInventoryFragment())
                .commit()
        } else {
            // si el inventario no esta vacío, mostramos el fragment de Inventory
            println("Inventario no vacío, mostrando el inventario")
            println("DEBUG: plantList: $plantList")
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