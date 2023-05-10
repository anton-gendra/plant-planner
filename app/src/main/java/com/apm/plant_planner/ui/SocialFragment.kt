package com.apm.plant_planner.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.apm.plant_planner.Post
import com.apm.plant_planner.R
import com.apm.plant_planner.model.Plant
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [SocialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_social, container, false)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton8).setOnClickListener {
            Log.d("btnSetup", "Selected")
            view.context.startActivity(Intent(view.context, Post::class.java))

        }

        val searchFriendsBtn: Button = view.findViewById(R.id.add_user_btn)
        searchFriendsBtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.navHostFragment, SearchFriendsFragment())
            transaction.commit()
        }

        val postButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton8)
        postButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.navHostFragment, PostFragment())
            transaction.commit()
        }

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_social, container, false)
    }
}