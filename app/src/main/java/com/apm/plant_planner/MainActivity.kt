package com.apm.plant_planner

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apm.plant_planner.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.apm.plant_planner.utils.*

class MainActivity : AppCompatActivity() {

    lateinit var currentPhotoPath: String
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        setNewTheme(this, "main")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.navHostFragment,
            EmptyInventoryFragment()).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        lateinit var selectedFragment: Fragment
        var activitySelected = 0;
        when (it.itemId) {
            R.id.page_inventory -> {
                selectedFragment = EmptyInventoryFragment()
            }
            R.id.page_calendar -> {
                selectedFragment = CalendarFragment()
            }
            R.id.page_camera -> {
                /*val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                }
                selectedFragment = CameraFragment()*/
                //startActivity(Intent(this, CameraActivity::class.java))
                //activitySelected = 1
                selectedFragment = CameraFragment()
            }
            R.id.page_social -> {
                selectedFragment = SocialFragment()
            }
            R.id.page_profile -> {
                selectedFragment = ProfileFragment()
            }
        }
        if (activitySelected == 0) {
            supportFragmentManager.beginTransaction().replace(R.id.navHostFragment, selectedFragment).commit()
        }
        true
    }

    override fun onResume() {
        if (themeHasChanged(this, "main")) {
            recreate()
        }
        super.onResume()
    }
}