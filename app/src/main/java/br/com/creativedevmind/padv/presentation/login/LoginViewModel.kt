package br.com.creativedevmind.padv.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()

    fun doLogin(username: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(Login(username, password))
        }
    }

}