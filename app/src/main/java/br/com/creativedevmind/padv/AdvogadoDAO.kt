package br.com.creativedevmind.padv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import com.google.firebase.firestore.FirebaseFirestore

@Dao
class AdvogadoDAO(private val firebaseFirestore: FirebaseFirestore) {

    fun getListaDeAdvogados(): LiveData<List<Advogado>>{
        var data = MutableLiveData<List<Advogado>>()

        firebaseFirestore.collection("advogados")
            .get()

        return data
    }
}