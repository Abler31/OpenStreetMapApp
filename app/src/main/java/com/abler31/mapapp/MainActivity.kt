package com.abler31.mapapp

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {
    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var markerList: ArrayList<MarkerModel>
    lateinit var myLocationOverlay: MyLocationNewOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_main)
        val zoomPlusButton = findViewById<ImageButton>(R.id.btn_zoom_plus)
        val zoomMinusButton = findViewById<ImageButton>(R.id.btn_zoom_minus)
        val locationButton = findViewById<ImageButton>(R.id.btn_location)
        val nextTrackerButton = findViewById<ImageButton>(R.id.btn_next_tracker)

        mMap = findViewById(R.id.osmmap)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.setMultiTouchControls(true)
        mMap.setBuiltInZoomControls(false);
        controller = mMap.controller

        zoomPlusButton.setOnClickListener {
            controller.zoomIn()
        }

        zoomMinusButton.setOnClickListener {
            controller.zoomOut()
        }

        //начальное позиционирование
        val mapPoint = GeoPoint(55.7833, 49.1017)
        controller.setZoom(15.5)
        controller.animateTo(mapPoint)

        //текущая геолокация
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        getCurrentLocation()
        locationButton.setOnClickListener {
            getCurrentLocation(true)
        }

        initMarkerList()
        markerList.forEach {
            setMarker(it.name, it.track, it.date, it.time, it.geoPoint)
        }

        //следующий трекер
        var trackerSelector = 0
        nextTrackerButton.setOnClickListener {
            myLocationOverlay.disableFollowLocation()
            if (trackerSelector == markerList.size) trackerSelector = 0
            mMap.controller.animateTo(markerList[trackerSelector++].geoPoint)
            mMap.controller.setZoom(15.5)
        }
    }

    private fun setMarker(
        name: String,
        track: String,
        date: String,
        time: String,
        geoPoint: GeoPoint
    ) {
        val marker = Marker(mMap)
        marker.position = geoPoint
        marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        val customInfoWindow =
            CustomInfoWindow(R.layout.info_window_layout, mMap, name, track, time)
        marker.infoWindow = customInfoWindow

        //overlay для обработки нажатия вне info window
        mMap.overlays.add(FullScreenOverlay(marker.infoWindow as CustomInfoWindow))

        val bottomSheetFragment = BottomSheetFragment(name, track, date, time)
        marker.setOnMarkerClickListener { mark, mapView ->
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            marker.showInfoWindow()
            return@setOnMarkerClickListener true
        }

        mMap.overlays.add(marker)
        mMap.invalidate()
    }

    private fun getCurrentLocation(zoom: Boolean = false) {
        val currentDraw =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_my_tracker_46dp, null)
        var currentIcon: Bitmap? = null
        if (currentDraw is BitmapDrawable) {
            currentIcon = currentDraw.bitmap
        }
        myLocationOverlay = MyLocationNewOverlay(mMap)
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.setPersonIcon(currentIcon)
        myLocationOverlay.setDirectionIcon(currentIcon)
        myLocationOverlay.enableMyLocation()
        mMap.overlays.add(myLocationOverlay)
        if (zoom) {
            mMap.controller.animateTo(myLocationOverlay.myLocation)
            mMap.controller.setZoom(15.5)
        }
    }

    private fun initMarkerList() {
        var id = 0
        markerList = arrayListOf(
            MarkerModel(
                id++,
                "Илья",
                "GPS",
                "15.09.23",
                "15:30",
                GeoPoint(55.77135008293359, 49.10390480122086)
            ),
            MarkerModel(
                id++,
                "Илья",
                "GPS",
                "15.09.23",
                "15:30",
                GeoPoint(55.78025708036169, 49.116782610051025)
            ),
            MarkerModel(
                id++,
                "Илья",
                "GPS",
                "15.09.23",
                "15:30",
                GeoPoint(55.79715863892295, 49.0988111057205)
            )
        )
    }
}