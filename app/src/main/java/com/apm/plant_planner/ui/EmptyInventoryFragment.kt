package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apm.plant_planner.R
import com.apm.plant_planner.SearchPlant

class EmptyInventoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_inventory, container, false)
    }



    fun searchPlant(view: View) {
        startActivity(Intent(this.requireContext(), SearchPlant::class.java))
    }
}