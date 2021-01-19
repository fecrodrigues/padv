package br.com.creativedevmind.padv.domain.usecase

import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.repository.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun create(user: User): RequestState<User> = userRepository.create(user)

}