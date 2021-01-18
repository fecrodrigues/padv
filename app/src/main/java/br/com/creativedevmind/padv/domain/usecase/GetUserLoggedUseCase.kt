package br.com.creativedevmind.padv.domain.usecase

import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.repository.UserRepository

class GetUserLoggedUseCase(
    private val userRepository: UserRepository
) {

    suspend fun getUserLogged(): RequestState<User> = userRepository.getUserLogged()

}