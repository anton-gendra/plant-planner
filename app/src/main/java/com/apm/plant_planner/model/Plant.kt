package com.apm.plant_planner.model

data class Plant (
    var plant_name: String,
    var plant_type: String,
    var bitmapFileName: String? = null,
    var location_home: PlantHomeLocation? = null,
    var watering_frequency_weeks: Int? = null,
)
