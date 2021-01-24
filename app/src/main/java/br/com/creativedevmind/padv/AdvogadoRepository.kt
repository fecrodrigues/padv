package br.com.creativedevmind.padv

import androidx.lifecycle.LiveData

class AdvogadoRepository(private val advogadoDAO: AdvogadoDAO) {
    var advogados: LiveData<List<Advogado>> = advogadoDAO.getListaDeAdvogados()
}