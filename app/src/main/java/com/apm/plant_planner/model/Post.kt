package com.apm.plant_planner.model

data class Post (
    var id: Double? = null,
    var title: String,
    var location: String? = null,
    var bitmap: String? = null,
    var author: String
    )