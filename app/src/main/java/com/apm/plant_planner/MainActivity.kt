package com.apm.plant_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apm.plant_planner.ui.CalendarFragment
import com.apm.plant_planner.ui.EmptyInventoryFragment
import com.apm.plant_planner.ui.SocialFragment
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
                Toast.makeText(applicationContext, "DEBUG: Inventory.", Toast.LENGTH_SHORT).show()
                selectedFragment = EmptyInventoryFragment()
            }
            R.id.page_calendar -> {
                Toast.makeText(applicationContext, "DEBUG: Calendar.", Toast.LENGTH_SHORT).show()
                selectedFragment = CalendarFragment()
            }
            R.id.page_camera -> {
                Toast.makeText(applicationContext, "Camera not implemented yet.", Toast.LENGTH_SHORT).show()
            }
            R.id.page_social -> {
                selectedFragment = SocialFragment()
            }
            R.id.page_camera -> {
                Toast.makeText(applicationContext, "Profile not implemented yet.", Toast.LENGTH_SHORT).show()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.navHostFragment, selectedFragment).commit()
        true
    }
}