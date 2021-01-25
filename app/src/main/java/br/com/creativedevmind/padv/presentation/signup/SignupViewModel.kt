package br.com.creativedevmind.padv.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.domain.entity.LawyerInfo
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.CreateUserUseCase
import kotlinx.coroutines.launch
import java.util.*

class SignupViewModel(
    private val createUserUseCase: CreateUserUseCase
): ViewModel() {

    val createState = MutableLiveData<RequestState<User>>()

    fun createUser(firstName: String, lastName: String, birthDate: Date, email: String, password: String,
                   phone: String, isPenal: Boolean, isTrabalhista: Boolean, isConsumidor: Boolean,
                isTributario: Boolean, isImobiliario: Boolean, biography: String, OABNumber: String, advocate: Boolean ) {
        viewModelScope.launch {

            var areasOfExpertise = mutableListOf<String>()
            var areaIndex: Int = 0

            if(isPenal) {
                areasOfExpertise.add(areaIndex++, "penal")
            }

            if(isTrabalhista) {
                areasOfExpertise.add(areaIndex++, "trabalhista")
            }

            if(isConsumidor) {
                areasOfExpertise.add(areaIndex++, "consumidor")
            }

            if(isImobiliario) {
                areasOfExpertise.add(areaIndex++, "imobiliario")
            }

            if(isTributario) {
                areasOfExpertise.add(areaIndex++, "tributario")
            }

            val login = Login(email, password)
            val lawyerInfo = LawyerInfo(areasOfExpertise, biography, OABNumber)
            var user = User(firstName, lastName, birthDate, email, phone, login, lawyerInfo, advocate)

            createState.value = createUserUseCase.create(user)
        }
    }

}