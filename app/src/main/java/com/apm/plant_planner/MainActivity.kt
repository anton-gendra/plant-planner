package com.apm.plant_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apm.plant_planner.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.navHostFragment,
            EmptyInventoryFragment()).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        lateinit var selectedFragment: Fragment
        when (it.itemId) {
            R.id.page_inventory -> {
                selectedFragment = EmptyInventoryFragment()
            }
            R.id.page_calendar -> {
                selectedFragment = CalendarFragment()
            }
            R.id.page_camera -> {
                selectedFragment = CameraFragment()
            }
            R.id.page_social -> {
                selectedFragment = SocialFragment()
            }
            R.id.page_profile -> {
                selectedFragment = ProfileFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.navHostFragment, selectedFragment).commit()
        true
    }
}