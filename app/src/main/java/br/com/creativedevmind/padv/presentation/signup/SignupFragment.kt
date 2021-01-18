package br.com.creativedevmind.padv.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.presentation.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class SignupFragment : BaseFragment() {

    override val layout = R.layout.fragment_signup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}