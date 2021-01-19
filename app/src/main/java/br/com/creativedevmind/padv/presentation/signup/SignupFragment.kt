package br.com.creativedevmind.padv.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.CreateUserUseCase
import br.com.creativedevmind.padv.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

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

    private lateinit var input_firstname: TextView
    private lateinit var input_lastname: TextView
    private lateinit var input_birthdate: TextView
    private lateinit var input_email: TextView
    private lateinit var input_phone: TextView
    private lateinit var input_npassword: TextView
    private lateinit var cb_penal: CheckBox
    private lateinit var cb_trabalhista: CheckBox
    private lateinit var cb_consumidor: CheckBox
    private lateinit var cb_tributario: CheckBox
    private lateinit var cb_imobiliario: CheckBox
    private lateinit var input_biography: TextView
    private lateinit var input_OABNumber: TextView
    private lateinit var btn_cadastrar: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

    }

    private fun setUpVariables(view: View) {
        input_firstname = view.findViewById(R.id.tx_cadastro)
        input_lastname = view.findViewById(R.id.input_lastname)

        input_birthdate = view.findViewById(R.id.input_birthdate)
        input_birthdate.addTextChangedListener(MascaraNumericaTextWatcher("##/##/####"))

        input_email = view.findViewById(R.id.input_email)

        input_phone = view.findViewById(R.id.input_phone)
        input_phone.addTextChangedListener(MascaraNumericaTextWatcher("(##) #####-####"))

        input_npassword = view.findViewById(R.id.input_npassword)
        cb_penal = view.findViewById(R.id.cb_penal)
        cb_trabalhista = view.findViewById(R.id.cb_trabalhista)
        cb_consumidor = view.findViewById(R.id.cb_consumidor)
        cb_tributario = view.findViewById(R.id.cb_tributario)
        cb_imobiliario = view.findViewById(R.id.cb_imobiliario)
        input_biography = view.findViewById(R.id.input_biography)

        input_OABNumber = view.findViewById(R.id.input_OABNumber)
        input_OABNumber.addTextChangedListener(MascaraNumericaTextWatcher("##################"))

        btn_cadastrar = view.findViewById(R.id.btn_cadastrar)
    }

    private fun setListeners() {

        btn_cadastrar.setOnClickListener {

            val birthdate_txt = input_birthdate.text.toString()
            lateinit var birthdate: Date

            if(birthdate_txt != null) {
                val format = SimpleDateFormat("dd/MM/yyyy");
                birthdate = format.parse(birthdate_txt)
            }

            signupViewModel.createUser(
                input_firstname.text.toString(),
                input_lastname.text.toString(),
                birthdate,
                input_email.text.toString(),
                input_npassword.text.toString(),
                input_phone.text.toString(),
                cb_penal.isChecked,
                cb_trabalhista.isChecked,
                cb_consumidor.isChecked,
                cb_tributario.isChecked,
                cb_imobiliario.isChecked,
                input_biography.text.toString(),
                input_OABNumber.text.toString()
            )
        }

    }

    private fun setObservers() {

        signupViewModel.createState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Loading -> showLoading("Cadastrando... Aguarde")
                is RequestState.Error -> showError(it.throwable)
            }
        })

    }

    private fun showSuccess() {
        hideLoading()
        findNavController().navigate(R.id.loginFragment)
    }

    private fun showError(throwable: Throwable) {
        hideLoading()
        showMessage(throwable.message)
    }
}