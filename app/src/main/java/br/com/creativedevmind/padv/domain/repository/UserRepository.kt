package br.com.creativedevmind.padv.domain.repository

import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.LawyerInfo
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.util.RequestState

interface UserRepository {

    suspend fun getUserLogged(): RequestState<User>
    suspend fun doLogin(login: Login): RequestState<User>
    suspend fun doLogout(): RequestState<Boolean>
    suspend fun create(user: User): RequestState<User>

}