package br.com.creativedevmind.padv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val repository: ProdutoRepository

    val advogados: LiveData<List<Advogado>>

    init {
//        val produtoDao = LembretedeComprasRoomDatabase.getDatabase(application).produtoDao()
//        repository = ProdutoRepository(produtoDao)

        var liveData = MutableLiveData<List<Advogado>>()
        var advogadosList = ArrayList<Advogado>()
        advogadosList.add(Advogado("Breno"))
        advogadosList.add(Advogado("Marino"))
        advogadosList.add(Advogado("Miguel"))
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