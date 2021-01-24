package br.com.creativedevmind.padv.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val logoutState = MutableLiveData<RequestState<Boolean>>()

    fun doLogout() {
        viewModelScope.launch {
            logoutState.value = logoutUseCase.doLogout()
        }
    }

}