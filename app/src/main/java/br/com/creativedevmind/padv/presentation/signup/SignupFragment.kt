package br.com.creativedevmind.padv.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.repository.UserRepository
import br.com.creativedevmind.padv.domain.usecase.CreateUserUseCase
import br.com.creativedevmind.padv.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class SignupFragment : BaseFragment() {

    override val layout = R.layout.fragment_signup

    private val signupViewModel: SignupViewModel by lazy {
        ViewModelProvider(
            this,
            SignupViewModelFactory(
                CreateUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(SignupViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

    }

    private fun setUpVariables(view: View) {

    }

    private fun setListeners() {

    }

    private fun setObservers() {

    }
}