package com.example.locationsharingapp_dipti_ict_amad_l4_04_23.Model_DIPTI_ICT_AMAD_L4_04_23

import com.google.firebase.database.PropertyName


data class User_DIPTI_ICT_AMAD_L4_04_23(
    val userId: String,
    @get:PropertyName("displayName")
    @set:PropertyName("displayName")
    var displayName: String = "",

    @get:PropertyName("email")
    @set:PropertyName("email")
    var email: String = "",

    @get:PropertyName("location")
    @set:PropertyName("location")
    var location: String = ""
) {
    // No-argument constructor
    constructor() : this("", "", "")
}
