package br.com.creativedevmind.padv.domain.entity

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Login (
    val username: String = "",
    @get:Exclude val password: String = ""
)