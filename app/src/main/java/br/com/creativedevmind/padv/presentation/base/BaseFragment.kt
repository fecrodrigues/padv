package br.com.creativedevmind.padv.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import br.com.creativedevmind.padv.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class BaseFragment : Fragment() {

    abstract val layout: Int
    private lateinit var loadingView : View

    private val baseViewModel : BaseViewModel by lazy {
        ViewModelProvider(
            this,
            BaseViewModelFactory()
        ).get( BaseViewModel ::class.java)
    }

    override fun onCreateView ( inflater: LayoutInflater , container: ViewGroup?,
                                savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate( layout, container, false)
        loadingView = inflater.inflate( R.layout.include_loading, container,
            false)
        screenRootView .addView(screenView)
        screenRootView .addView(loadingView )
        registerObserver()
        return screenRootView
    }

    private fun registerObserver() {

    }

    override fun onResume() {
        super.onResume()
    }

    fun showLoading(message: String = "Processando a requisição") {
        loadingView.visibility = View.VISIBLE
        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text  = message
    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message,
            Toast.LENGTH_SHORT).show()
    }

}