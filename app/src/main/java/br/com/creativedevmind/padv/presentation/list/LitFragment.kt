package br.com.creativedevmind.padv.presentation.list

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.presentation.base.BaseViewModel
import br.com.creativedevmind.padv.presentation.base.BaseViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class ListFragment : Fragment() {

    private val listViewModel : ListViewModel by lazy {
        ViewModelProvider(
                this,
                ListViewModelFactory()
        ).get( ListViewModel ::class.java)
    }
}