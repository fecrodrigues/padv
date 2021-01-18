package br.com.creativedevmind.padv.presentation.base.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.domain.usecase.GetUserLoggedUseCase

class BaseAuthViewModelFactory(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetUserLoggedUseCase::class.java).newInstance(getUserLoggedUseCase)
    }

}