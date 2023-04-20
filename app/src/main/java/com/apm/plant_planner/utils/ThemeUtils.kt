package com.apm.plant_planner.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.apm.plant_planner.R

fun themeHasChanged(context: Context, activityName: String): Boolean {
    val pref: SharedPreferences = context.getSharedPreferences("plant-planner-style", Context.MODE_PRIVATE)
    return !pref.getBoolean(activityName.plus("_theme_adopted"), true)
}

fun setNewTheme(context: Context, activityName: String) {
    val pref: SharedPreferences = context.getSharedPreferences("plant-planner-style", Context.MODE_PRIVATE)
    val theme = pref.getInt("theme", -1)
    if (activityName == "" && theme != -1) {
        context.setTheme(theme)
        return
    }
    if (theme != -1) {
        context.setTheme(theme)
        with (pref.edit()) {
            putBoolean(activityName.plus("_theme_adopted"), true)
            commit()
        }
    }
}