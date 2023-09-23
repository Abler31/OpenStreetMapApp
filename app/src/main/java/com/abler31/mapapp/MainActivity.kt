package com.abler31.mapapp

import android.graphics.Rect
import android.location.GpsStatus
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageButton

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {
    lateinit var mMap: MapView
    lateinit var controller: IMapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_main)
        val zoomPlusButton = findViewById<ImageButton>(R.id.btn_zoom_plus)
        val zoomMinusButton = findViewById<ImageButton>(R.id.btn_zoom_minus)

        mMap = findViewById(R.id.osmmap)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())
        mMap.setBuiltInZoomControls(false);
        controller = mMap.controller

        zoomPlusButton.setOnClickListener{
            controller.zoomIn()
        }

        zoomMinusButton.setOnClickListener{
            controller.zoomOut()
        }

        val mapPoint = GeoPoint(55.7833, 49.1017)

        controller.setZoom(15.5)


        controller.animateTo(mapPoint)
    }
}