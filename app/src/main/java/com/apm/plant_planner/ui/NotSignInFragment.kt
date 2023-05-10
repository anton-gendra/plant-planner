package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.apm.plant_planner.LoginActivity
import com.apm.plant_planner.R


class NotSignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLoc = inflater.inflate(R.layout.fragment_not_sign_in, container, false)
        val btnLogIn: Button = viewLoc.findViewById(R.id.iniciarSesion)

        btnLogIn.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return viewLoc
    }
}