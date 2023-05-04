package com.apm.plant_planner.model

import android.graphics.Bitmap
import android.os.Parcelable

data class Plant (
    var plant_name: String,
    var plant_type: String,
    var bitmap: Bitmap? = null,
    var location_home: PlantHomeLocation? = null,
    var watering_frequency_weeks: Int? = null,
)
