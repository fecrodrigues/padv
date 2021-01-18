package br.com.creativedevmind.padv

import androidx.lifecycle.LiveData

class AdvogadoRepository(private val advogadoDAO: AdvogadoDAO) {
    val advogados: LiveData<List<Advogado>> = advogadoDAO.getListaDeAdvogados()
}