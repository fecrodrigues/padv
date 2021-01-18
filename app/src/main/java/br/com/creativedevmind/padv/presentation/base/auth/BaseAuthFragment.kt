package br.com.creativedevmind.padv.presentation.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.GetUserLoggedUseCase
import br.com.creativedevmind.padv.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {

    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
                this,
                BaseAuthViewModelFactory(
                        GetUserLoggedUseCase(
                                UserRepositoryImpl(
                                        UserRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        )
                )
        ).get(BaseAuthViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        registerObserver()

        baseAuthViewModel.getUserLogged()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {

        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    findNavController().navigate(
                            R.id.login_navigation, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                    ))
                }
            }
        })
    }
}