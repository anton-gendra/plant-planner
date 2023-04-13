package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.apm.plant_planner.Inventory
import com.apm.plant_planner.R
import com.apm.plant_planner.SearchPlant

class EmptyInventoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_empty_inventory, container, false)
        val searchButton: Button = view.findViewById(R.id.searchView)
        searchButton.setOnClickListener {
            val intent = Intent(requireActivity(), SearchPlant::class.java)
            startActivity(intent)
        }
        return view
    }
}