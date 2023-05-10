package com.apm.plant_planner.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.Settings

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("username")) {
            // si el inventario esta vacío, mostramos el fragment de EmptyInventory
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, NotSignInFragment())
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val settingsBtn: Button = view.findViewById(R.id.access_settings_btn)
        settingsBtn.setOnClickListener {
            Toast.makeText(activity, "Accessing settings", Toast.LENGTH_SHORT).show()
            activity?.startActivity(Intent(activity, Settings::class.java))
        }

        return view
    }
}