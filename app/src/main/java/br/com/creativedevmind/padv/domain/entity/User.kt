package br.com.creativedevmind.padv.domain.entity

import java.util.*

data class User (
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: Date? = null,
    val email: String = "",
    val phone: String = "",
    val accessInfo: Login = Login(),
    val lawyerInfo: LawyerInfo = LawyerInfo(),
    val advocate: Boolean? = false
)