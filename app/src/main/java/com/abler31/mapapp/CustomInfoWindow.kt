package com.abler31.mapapp

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(layoutResId: Int, mapView: MapView) : InfoWindow(layoutResId, mapView) {

    private val myView: View = LayoutInflater.from(mapView.context).inflate(layoutResId, null)
    private val name: TextView = myView.findViewById(R.id.tv_window_name) // Replace with your TextView's ID
    private val info: TextView = myView.findViewById(R.id.tv_window_info) // Replace with your TextView's ID

    // Method to open the info window
    fun openInfoWindow(marker: Marker) {
        onOpen(marker)
    }

    override fun onOpen(item: Any?) {
        if (item is Marker) {
            name.text = "Илья"
            info.text = "GPS, 15:30"
        }
    }

    override fun onClose() {
        // Cleanup if needed
    }



}