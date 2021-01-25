package br.com.creativedevmind.padv.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase

class ProfileViewModelFactory (
    private val logoutUseCase: LogoutUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LogoutUseCase::class.java).newInstance(logoutUseCase)
    }

}