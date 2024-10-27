package com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Viewmodel_DIPTI_ICT_AMAD_L4_04_23

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel_DIPTI_ICT_AMAD_L4_04_23: ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(it.exception?.message ?: "Login failed.")
                }
            }
    }
    fun register(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(it.exception?.message ?: "Registration failed.")
                }
            }
    }
    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: ""
    }
    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}