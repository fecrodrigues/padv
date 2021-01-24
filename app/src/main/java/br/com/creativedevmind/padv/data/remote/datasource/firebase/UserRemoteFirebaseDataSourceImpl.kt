package br.com.creativedevmind.padv.data.remote.datasource.firebase

import br.com.creativedevmind.padv.data.remote.datasource.UserRemoteDataSource
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.LawyerInfo
import br.com.creativedevmind.padv.domain.entity.Login
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.exception.UserNotLoggedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRemoteFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {

        return try {

            firebaseAuth.currentUser?.reload()
            val userID = firebaseAuth.currentUser?.uid

            if(userID == null) {
                return RequestState.Error(UserNotLoggedException())
            } else {

                val user = firebaseFirestore
                    .collection("users")
                    .document(userID)
                    .get()
                    .await()

                if(user.exists()) {
                    RequestState.Success(user.toObject(User::class.java)!!)
                } else {
                    RequestState.Error(Exception("Usuário não encontrado"))
                }


            }

        } catch (e: Exception) {
            return RequestState.Error(e)
        }

    }

    override suspend fun doLogin(login: Login): RequestState<User> {

        return try {

            firebaseAuth.signInWithEmailAndPassword(
                login.username,
                login.password
            ).await()

           this.getUserLogged()

        } catch (e: Exception) {
            return RequestState.Error(e)
        }

    }

    override suspend fun doLogout(): RequestState<Boolean> {

        return try {
            firebaseAuth.signOut()
            RequestState.Success(true)
        } catch (e: Exception) {
            return RequestState.Error(e)
        }

    }

    override suspend fun create(user: User): RequestState<User> {
        return try {

            if(user.accessInfo != null) {
                firebaseAuth.createUserWithEmailAndPassword(user.accessInfo.username, user.accessInfo.password).await()
            }

            val userID = firebaseAuth.currentUser?.uid

            if (userID == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                firebaseFirestore
                        .collection("users")
                        .document(userID)
                        .set(user)
                        .await()

                RequestState.Success(user)
            }

        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }


}