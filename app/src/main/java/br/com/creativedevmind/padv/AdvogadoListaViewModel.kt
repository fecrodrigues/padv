package br.com.creativedevmind.padv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdvogadoListaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AdvogadoRepository

    val advogados: LiveData<List<Advogado>>

    init {
        val advogadoDAO = AdvogadoRoomDatabase.getDatabase(application).advogadoDAO()
        repository = AdvogadoRepository(advogadoDAO)

//        var liveData = MutableLiveData<List<Advogado>>()
//        var advogadosList = ArrayList<Advogado>()
//        advogadosList.add(Advogado(1L,"Breno","","",ArrayList(),0F,""))
//        advogadosList.add(Advogado(2L,"Marino","","",ArrayList(),0F,""))
//        advogadosList.add(Advogado(3L,"Miguel","","",ArrayList(),0F,""))
//        liveData.postValue(advogadosList)

        advogados = repository.advogados
    }

    fun insert(advogado: Advogado) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(produto)
    }

    fun apagar() = viewModelScope.launch(Dispatchers.IO) {
//        repository.apagar()
    }

}