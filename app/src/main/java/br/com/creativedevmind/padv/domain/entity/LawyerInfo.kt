package br.com.creativedevmind.padv.domain.entity

data class LawyerInfo(
    val areasOfExpertise: List<String> = emptyList<String>(),
    val biography: String = "",
    val oabNumber: String = ""
)