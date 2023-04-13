package com.apm.plant_planner.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apm.plant_planner.Post
import com.apm.plant_planner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [SocialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_social, container, false)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton8).setOnClickListener {
            Log.d("btnSetup", "Selected")
            view.context.startActivity(Intent(view.context, Post::class.java))

        }

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_social, container, false)
    }
}