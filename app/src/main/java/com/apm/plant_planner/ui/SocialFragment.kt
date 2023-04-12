package com.apm.plant_planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apm.plant_planner.R

/**
 * A simple [Fragment] subclass.
 * Use the [SocialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social, container, false)
    }
}