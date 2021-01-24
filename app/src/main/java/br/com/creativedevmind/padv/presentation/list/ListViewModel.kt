package br.com.creativedevmind.padv.presentation.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.Advogado
import br.com.creativedevmind.padv.AdvogadoRepository
import br.com.creativedevmind.padv.AdvogadoRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AdvogadoRepository

    val advogados: LiveData<List<Advogado>>

    init {
        val advogadoDAO = AdvogadoRoomDatabase.getDatabase(application).advogadoDAO()
        repository = AdvogadoRepository(advogadoDAO)

        var liveData = MutableLiveData<List<Advogado>>()
        var advogadosList = ArrayList<Advogado>()
        advogadosList.add(Advogado("","Breno","",ArrayList(),0F,""))
        advogadosList.add(Advogado("","Marino","",ArrayList(),0F,""))
        advogadosList.add(Advogado("","Miguel","",ArrayList(),0F,""))
        liveData.postValue(advogadosList)

        advogados = liveData
    }

    fun insert(advogado: Advogado) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(produto)
    }

    fun apagar() = viewModelScope.launch(Dispatchers.IO) {
//        repository.apagar()
    }

}