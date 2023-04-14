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
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {

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