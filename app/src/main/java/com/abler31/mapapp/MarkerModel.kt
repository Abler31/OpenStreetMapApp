package com.abler31.mapapp

import org.osmdroid.util.GeoPoint

data class MarkerModel(
    val id: Int,
    val name: String,
    val track: String,
    val date: String,
    val time: String,
    val geoPoint: GeoPoint
)
