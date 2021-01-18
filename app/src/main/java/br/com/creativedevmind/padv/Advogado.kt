package br.com.creativedevmind.padv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Advogado(
    val nome: String,
    val descricaoResumida: String,
    val descricaoCompleta: String,
    val areasDeAtuacao: List<String>,
    val avaliacao: Float,
    val foto: String
)