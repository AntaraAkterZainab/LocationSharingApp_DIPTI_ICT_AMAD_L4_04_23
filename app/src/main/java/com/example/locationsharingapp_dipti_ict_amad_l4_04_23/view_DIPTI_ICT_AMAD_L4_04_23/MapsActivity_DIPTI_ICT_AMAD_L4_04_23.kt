package com.example.locationsharingapp_dipti_ict_amad_l4_04_23.view_DIPTI_ICT_AMAD_L4_04_23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.findmyfriende.Viewmodel.FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.R
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.databinding.ActivityMapsDiptiIctAmadL40423Binding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity_DIPTI_ICT_AMAD_L4_04_23 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val binding by lazy {
        ActivityMapsDiptiIctAmadL40423Binding.inflate(layoutInflater)
    }
    private lateinit var firestoreViewModel: FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnZoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        firestoreViewModel.getAllUsers(this) {
            for (user in it) {
                val userLocation = user.location
                val latLng = parseLocation(userLocation)
                if (userLocation.isEmpty()||userLocation == "Don't found any location yet"||userLocation == "Location not available") {
                    LatLng(37.4220936, -122.083922)
                }else{
                    val markerOptions = MarkerOptions().position(latLng).title(user.displayName)
                    googleMap.addMarker(markerOptions)
                }
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                googleMap.animateCamera(cameraUpdate)
            }
        }
    }

    private fun parseLocation(location: String): LatLng {
        val latLngSplit = location.split(", ")
        val latitude = latLngSplit[0].substringAfter("Lat: ").toDouble()
        val longitude = latLngSplit[1].substringAfter("Long: ").toDouble()
        return LatLng(latitude, longitude)
    }
}