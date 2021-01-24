package br.com.creativedevmind.padv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Dao
class AdvogadoDAO(private val firebaseFirestore: FirebaseFirestore) {

    fun getListaDeAdvogados(): LiveData<List<Advogado>>{
        var data = MutableLiveData<List<Advogado>>()

        val obj = firebaseFirestore.collection("advogados")
            .document()
            .get()

        data.setValue(obj.to(List::class.java) as List<Advogado>?)

        return data
    }
}