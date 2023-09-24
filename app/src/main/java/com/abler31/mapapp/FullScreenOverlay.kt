package com.abler31.mapapp

import android.view.MotionEvent
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay

class FullScreenOverlay(private val infoWindow: CustomInfoWindow) : Overlay() {

    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
        if (infoWindow.isOpen) {
            infoWindow.close()
            return true
        }
        return false
    }
}