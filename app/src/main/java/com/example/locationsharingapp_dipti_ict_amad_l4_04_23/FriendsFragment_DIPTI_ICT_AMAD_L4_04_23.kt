package com.example.locationsharingapp_dipti_ict_amad_l4_04_23

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Viewmodel_DIPTI_ICT_AMAD_L4_04_23.AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.findmyfriende.Viewmodel.FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Viewmodel_DIPTI_ICT_AMAD_L4_04_23.LocationViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Adapter_DIPTI_ICT_AMAD_L4_04_23.UserAdapter_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.databinding.FragmentFriendsDIPTIICTAMADL40423Binding
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.view_DIPTI_ICT_AMAD_L4_04_23.MapsActivity_DIPTI_ICT_AMAD_L4_04_23
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class FriendsFragment_DIPTI_ICT_AMAD_L4_04_23 : Fragment() {
    private lateinit var binding: FragmentFriendsDIPTIICTAMADL40423Binding
    private lateinit var firestoreViewModel: FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var authenticationViewModel: AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var userAdapter: UserAdapter_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var locationViewModel: LocationViewModel_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFriendsDIPTIICTAMADL40423Binding.inflate(inflater,container, false)

        firestoreViewModel = ViewModelProvider(this)[FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java]
        locationViewModel = ViewModelProvider(this)[LocationViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java]
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted
            getLocation()
        }
        userAdapter = UserAdapter_DIPTI_ICT_AMAD_L4_04_23(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fetchUsers()

        binding.locationBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity_DIPTI_ICT_AMAD_L4_04_23::class.java))
        }


        return binding.root
    }

    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()){
            userAdapter.updateData(it)
        }
    }

    private fun getLocation() {
        locationViewModel.getLastLocation {
            // Save location to Firestore for the current user
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

}