package br.com.creativedevmind.padv.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.domain.usecase.CreateUserUseCase

class SignupViewModelFactory(
    private val createUserUseCase: CreateUserUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateUserUseCase::class.java).newInstance(createUserUseCase)
    }

}