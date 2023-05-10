package com.apm.plant_planner.ui

data class PlantResponse(
    val id: String,
    val scientificNameWithoutAuthor: String,
    val scientificNameAuthorship: String,
    val gbifId: Long

) {
}