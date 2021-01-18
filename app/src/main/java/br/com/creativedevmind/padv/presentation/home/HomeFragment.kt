package br.com.creativedevmind.padv.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.exception.UserNotLoggedException
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase
import br.com.creativedevmind.padv.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                LogoutUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(HomeViewModel::class.java)
    }

    private lateinit var btn_logout: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

    }

    private fun setUpVariables(view: View) {
        btn_logout = view.findViewById(R.id.btn_logout)
    }

    private fun setListeners() {

        btn_logout.setOnClickListener {
            homeViewModel.doLogout()
        }

    }

    private fun setObservers() {

        homeViewModel.logoutState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Loading -> showMessage("Efetuando logout... Aguarde")
                is RequestState.Error -> showError(it.throwable)
            }
        })

    }

    private fun showSuccess() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdForArguments == null) {
            findNavController().navigate(R.id.login_navigation)
        } else {
            findNavController().popBackStack(navIdForArguments, false)
        }
    }

    private fun showError(throwable: Throwable) {
        hideLoading()
        showMessage(throwable.message)
    }

}