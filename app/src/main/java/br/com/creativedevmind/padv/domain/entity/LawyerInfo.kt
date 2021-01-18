package br.com.creativedevmind.padv.domain.entity

data class LawyerInfo(
    val areasOfExpertise: List<String>,
    val biography: String,
    val OABNumber: String
)