package br.com.creativedevmind.padv.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.exception.UserNotLoggedException
import br.com.creativedevmind.padv.domain.usecase.LoginUseCase
import br.com.creativedevmind.padv.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class LoginFragment: BaseFragment() {

    override val layout = R.layout.fragment_login

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(
                LoginUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(LoginViewModel::class.java)
    }

    private lateinit var input_username: EditText
    private lateinit var input_password: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_signup: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

        registerBackPressedAction()
    }

    private fun setUpVariables(view: View) {
        input_username = view.findViewById(R.id.input_username)
        input_password = view.findViewById(R.id.input_password)
        btn_login = view.findViewById(R.id.btn_login)
        btn_signup = view.findViewById(R.id.btn_signup)
    }

    private fun setListeners() {

        btn_login.setOnClickListener {
            var username = input_username.text.toString()
            var password = input_password.text.toString()

            loginViewModel.doLogin(username, password)
        }

        btn_signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

    }

    private fun setObservers() {

        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Loading -> showLoading("Efetuando login... Aguarde")
                is RequestState.Error -> showError(it.throwable)
            }
        })

    }

    private fun showSuccess() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
        findNavController().navigate(R.id.main_navigation)
    }

    private fun showError(throwable: Throwable) {
        hideLoading()

        when(throwable) {
            is UserNotLoggedException -> {}
            else -> {
                input_username.error = null
                input_password.error = null

                showMessage(throwable.message)
            }
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}