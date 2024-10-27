package com.example.locationsharingapp_dipti_ict_amad_l4_04_23

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Viewmodel_DIPTI_ICT_AMAD_L4_04_23.AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.findmyfriende.Viewmodel.FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Viewmodel_DIPTI_ICT_AMAD_L4_04_23.LocationViewModel_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.databinding.FragmentProfileDIPTIICTAMADL40423Binding
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.view_DIPTI_ICT_AMAD_L4_04_23.LoginActivity_DIPTI_ICT_AMAD_L4_04_23
import com.example.locationsharingapp_dipti_ict_amad_l4_04_23.view_DIPTI_ICT_AMAD_L4_04_23.MainActivity_DIPTI_ICT_AMAD_L4_04_23
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment_DIPTI_ICT_AMAD_L4_04_23 : Fragment() {

    private lateinit var binding: FragmentProfileDIPTIICTAMADL40423Binding
    private lateinit var authViewModel: AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var firestoreViewModel: FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23
    private lateinit var locationViewModel: LocationViewModel_DIPTI_ICT_AMAD_L4_04_23
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileDIPTIICTAMADL40423Binding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(this).get(AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel_DIPTI_ICT_AMAD_L4_04_23::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity_DIPTI_ICT_AMAD_L4_04_23::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity_DIPTI_ICT_AMAD_L4_04_23::class.java))
        }

        loadUserInfo()
        binding.updateBtn.setOnClickListener {
            val newName = binding.NameEt.text.toString()
            val newLocation = binding.Loaction.text.toString()

            updateBtn(newName, newLocation)
        }

        return binding.root
    }


    private fun updateBtn(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity_DIPTI_ICT_AMAD_L4_04_23::class.java))
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if(currentUser != null) {
            binding.emailEt.setText(currentUser.email)
            firestoreViewModel.getUser(requireContext(), currentUser.uid){ user ->
                if (currentUser.displayName != null) {
                    binding.NameEt.setText(currentUser.displayName)
                }
            }
        }else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }

    }
}