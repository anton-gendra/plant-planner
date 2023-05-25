package com.apm.plant_planner.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.model.Plant
import com.apm.plant_planner.model.PlantAdapter
import com.google.gson.Gson

class InventoryFragment : Fragment() {

    var plantList = emptyList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val plantListJson = sharedPreferences.getString("plant_list", "")
        plantList = Gson().fromJson(plantListJson, Array<Plant>::class.java).toList()

        if (plantList.isEmpty()) {
            // si el inventario esta vacío, mostramos el fragment de EmptyInventory
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

        var plantAdapter = PlantAdapter(requireContext(), plantList)
        val plantas = view.findViewById<ListView>(R.id.plantas)

        plantas.adapter = plantAdapter

        plantas.setOnItemClickListener() { parent, view, position, id ->
            val intent = Intent(requireActivity(), PlantAtributtes::class.java)
            intent.putExtra("EXTRA_MODE", "edit")
            intent.putExtra("EXTRA_NAME", plantList[position].plant_name)
            intent.putExtra("EXTRA_TYPE", plantList[position].plant_type)
            intent.putExtra("EXTRA_BITMAP_FILE_NAME", plantList[position].bitmapFileName)
            intent.putExtra("EXTRA_LOCATION_HOME", plantList[position].location_home)

            startActivity(intent)
        }

        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(requireActivity(), PlantAtributtes::class.java)
            startActivity(intent)
        }

        return view
    }
}