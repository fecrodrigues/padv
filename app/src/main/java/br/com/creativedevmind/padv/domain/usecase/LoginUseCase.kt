package br.com.creativedevmind.padv.domain.usecase

import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.repository.UserRepository

class LoginUseCase(
        private val userRepository: UserRepository
) {

    suspend fun doLogin(login: Login): RequestState<User> = userRepository.doLogin(login)

}