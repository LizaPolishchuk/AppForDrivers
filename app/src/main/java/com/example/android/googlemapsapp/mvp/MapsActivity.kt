package com.example.android.googlemapsapp.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.android.googlemapsapp.R
import com.example.android.googlemapsapp.data.AllData
import com.example.android.googlemapsapp.utils.CheckingConnection
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, Contract.View {

    private val dataList: MutableList<AllData.MyData> = mutableListOf()
    private val presenter: Presenter= Presenter(this)

    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        presenter
        /**Getting data from the model*/
        presenter.getDataFromModel()
    }

    override fun showData(data: MutableList<AllData.MyData>) {
        /**If there is no internet connection and no data in the database, show the TextView*/
        if (data.size == 0 && !CheckingConnection.hasConnected()) {
            tvConnection.visibility = View.VISIBLE
            mapContainer.visibility = View.GONE
        }
        /**Else show the mapFragment*/
        else {
            dataList.addAll(data)
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val markers = Array(dataList.size) { MarkerOptions() }
       /**For each item in the list added the marker with title and icon*/
        for (i in 0 until dataList.size) {
            val data: AllData.MyData = dataList[i]
            val latLng = LatLng(data.lat, data.lng)
            val title = data.name ?: "$latLng"
            Log.d("myLogs", "category: ${data.categoryId}")
            val icon: BitmapDescriptor = if (data.categoryId == 1) BitmapDescriptorFactory.fromResource(R.drawable.parking) else BitmapDescriptorFactory.fromResource(R.drawable.gas_station)

            markers[i] = MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .icon(icon)
            mMap.addMarker(markers[i])
        }

        mMap.uiSettings.isZoomControlsEnabled = true

        val cameraPosition = CameraPosition.builder()
                .target(markers[0].position)
                .bearing(45F)
                .zoom(5F)
                .tilt(20F)
                .build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.animateCamera(cameraUpdate)

        mMap.setOnMarkerClickListener {
            it.showInfoWindow()
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(it.position, 15F)
            googleMap.animateCamera(cameraUpdate)
            true
        }
    }

    /**If there is TextView with information about absent Internet connection we can touch the screen to refresh*/
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (tvConnection.visibility == View.VISIBLE) {
            this.recreate()
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
