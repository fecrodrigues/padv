package br.com.creativedevmind.padv.presentation.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel (
    private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModel() {

    var userLoggedState = MutableLiveData<RequestState<User>>()

    fun getUserLogged() {
        viewModelScope.launch {
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }


}