package br.com.creativedevmind.padv.domain.usecase

import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.repository.UserRepository

class LogoutUseCase(
    private val userRepository: UserRepository
) {

    suspend fun doLogout(): RequestState<Boolean> = userRepository.doLogout()

}