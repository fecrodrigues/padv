package br.com.creativedevmind.padv.data.repository

import br.com.creativedevmind.padv.data.remote.datasource.UserRemoteDataSource
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(login: Login): RequestState<User> {
        return userRemoteDataSource.doLogin(login)
    }

    override suspend fun doLogout(): RequestState<Boolean> {
        return userRemoteDataSource.doLogout()
    }

    override suspend fun create(user: User): RequestState<User> {
        return userRemoteDataSource.create(user)
    }

}