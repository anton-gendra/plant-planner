package com.apm.plant_planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var calendarFragment = CalendarFragment();
        var socialFragment = SocialFragment();
        var inventoryFragment = InventoryFragment();

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.page_calendar -> {
                    setCurrentFragment(calendarFragment);
                    Toast.makeText(applicationContext, "DEBUG: Calendar.", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.page_social -> {
                    setCurrentFragment(socialFragment);
                    Toast.makeText(applicationContext, "DEBUG: Social.", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.page_inventory -> {
                    setCurrentFragment(inventoryFragment);
                    Toast.makeText(applicationContext, "DEBUG: Inventory.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Toast.makeText(applicationContext, "DEBUG: Window not joined yet.", Toast.LENGTH_SHORT).show()
                    false
                }
            }

        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            commit()
        }
    }

}