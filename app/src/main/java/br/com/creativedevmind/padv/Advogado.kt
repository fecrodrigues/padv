package br.com.creativedevmind.padv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_advogado")
data class Advogado(
    @PrimaryKey @ColumnInfo(name = "nome_advogado")val nome: String
)