package br.com.creativedevmind.padv.presentation.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.domain.entity.LawyerInfo
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel (
    private val logoutUseCase: LogoutUseCase
)  : ViewModel() {
    val logoutState = MutableLiveData<RequestState<Boolean>>()
    private val db = FirebaseFirestore.getInstance()


    fun updateUser(uid: String, firstName: String, lastName: String, birthDate: Date, phone: String, email: String
            ,isPenal: Boolean, isTrabalhista: Boolean, isConsumidor: Boolean, isTributario: Boolean
                   ,isImobiliario: Boolean, biography: String) {

        //, isPenal: Boolean, isTrabalhista: Boolean, isConsumidor: Boolean, isTributario: Boolean, isImobiliario: Boolean, biography: String
        viewModelScope.launch {
            var areasOfExpertise = mutableListOf<String>()
            var areaIndex: Int = 0

            if (isPenal) {
                areasOfExpertise.add(areaIndex++, "penal")
            }

            if (isTrabalhista) {
                areasOfExpertise.add(areaIndex++, "trabalhista")
            }

            if (isConsumidor) {
                areasOfExpertise.add(areaIndex++, "consumidor")
            }

            if (isImobiliario) {
                areasOfExpertise.add(areaIndex++, "imobiliario")
            }

            if (isTributario) {
                areasOfExpertise.add(areaIndex++, "tributario")
            }

            //"lawyerInfo.areasOfExpertise", FieldValue.delete(),
            //, "lawyerInfo.areasOfExpertise", FieldValue.arrayUnion(areasOfExpertise)
            //"lawyerInfo.areasOfExpertise", FieldValue.arrayUnion(areasOfExpertise)

            val updateUser = db.collection("users").document(uid)
            updateUser.get().addOnSuccessListener { document ->
                val userData = document.toObject(User::class.java)

                println("OABNumber: " + userData?.lawyerInfo?.oabNumber)

                val lawyerInfo = LawyerInfo(areasOfExpertise, biography, userData?.lawyerInfo?.oabNumber.toString())

                updateUser.update("firstName", firstName, "lastName", lastName, "birthDate", birthDate, "phone",
                        phone, "email", email,  "biography",
                        biography, "lawyerInfo", lawyerInfo)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            }
        }
    }

    fun doLogout() {
        viewModelScope.launch {
            logoutState.value = logoutUseCase.doLogout()
        }
    }

}