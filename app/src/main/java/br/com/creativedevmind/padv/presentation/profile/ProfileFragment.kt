package br.com.creativedevmind.padv.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase
import br.com.creativedevmind.padv.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class ProfileFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_profile

    private val db = FirebaseFirestore.getInstance()
    private val userLogged = FirebaseAuth.getInstance().currentUser?.uid

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileViewModelFactory(
                LogoutUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(ProfileViewModel::class.java)
    }

    private lateinit var input_firstname: TextView
    private lateinit var input_lastname: TextView
    private lateinit var input_birthdate: TextView
    private lateinit var input_phone: TextView
    private lateinit var input_email: TextView
    private lateinit var textView4: TextView
    private lateinit var cb_penal: CheckBox
    private lateinit var cb_trabalhista: CheckBox
    private lateinit var cb_consumidor: CheckBox
    private lateinit var cb_tributario: CheckBox
    private lateinit var cb_imobiliario: CheckBox
    private lateinit var input_biography: TextView

    private lateinit var btn_update: Button
    private lateinit var btn_logout: Button



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

    }


    private fun setUpVariables(view: View) {

        input_firstname = view.findViewById(R.id.input_firstname)
        input_lastname = view.findViewById(R.id.input_lastname)
        input_birthdate = view.findViewById(R.id.input_birthdate)
        input_birthdate.addTextChangedListener(MascaraNumericaTextWatcher("##/##/####"))
        input_phone = view.findViewById(R.id.input_phone)
        input_phone.addTextChangedListener(MascaraNumericaTextWatcher("(##) #####-####"))
        input_email = view.findViewById(R.id.input_email)
        textView4 = view.findViewById(R.id.textView4)
        cb_penal = view.findViewById(R.id.cb_penal)
        cb_trabalhista = view.findViewById(R.id.cb_trabalhista)
        cb_consumidor = view.findViewById(R.id.cb_consumidor)
        cb_tributario = view.findViewById(R.id.cb_tributario)
        cb_imobiliario = view.findViewById(R.id.cb_imobiliario)
        input_biography = view.findViewById(R.id.input_biography)


        btn_update = view.findViewById(R.id.btn_update)
        btn_logout = view.findViewById(R.id.btn_logout)


        val docRef = userLogged?.let { db.collection("users").document(it) }
        if (docRef != null) {
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("exist", "DocumentSnapshot data: ${document.data} = User: ${userLogged}")

                    val userData = document.toObject(User::class.java)

                    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val birthDate: String = formatter.format(userData?.birthDate)

                    input_firstname.text = userData?.firstName
                    input_lastname.text = userData?.lastName
                    input_birthdate.text = birthDate
                    input_phone.text = userData?.phone
                    input_email.text = userData?.email
                    if (userData?.lawyerInfo?.areasOfExpertise?.contains("penal") == true) {
                        cb_penal.isChecked = true
                    }
                    if (userData?.lawyerInfo?.areasOfExpertise?.contains("trabalhista") == true) {
                        cb_trabalhista.isChecked = true
                    }
                    if (userData?.lawyerInfo?.areasOfExpertise?.contains("consumidor") == true) {
                        cb_consumidor.isChecked = true
                    }
                    if (userData?.lawyerInfo?.areasOfExpertise?.contains("imobiliario") == true) {
                        cb_imobiliario.isChecked = true
                    }
                    if (userData?.lawyerInfo?.areasOfExpertise?.contains("tributario") == true) {
                        cb_tributario.isChecked = true
                    }
                    input_biography.text = userData?.lawyerInfo?.biography

                    println(userData?.lawyerInfo?.areasOfExpertise?.contains("penal"))

                    if (userData?.advocate == false) {
                        textView4.visibility = View.GONE
                        cb_penal.visibility = View.GONE
                        cb_trabalhista.visibility = View.GONE
                        cb_consumidor.visibility = View.GONE
                        cb_imobiliario.visibility = View.GONE
                        cb_tributario.visibility = View.GONE
                        input_biography.visibility = View.GONE
                        btn_update.visibility = View.VISIBLE
                        //result_list.visibility = View.INVISIBLE
                    }
                } else {
                    Log.d("noexist", "No such document")
                }
            }
                .addOnFailureListener { exception ->
                    Log.d("errordb", "get failed with", exception)
                }
        }


    }

    private fun setListeners() {

        btn_logout.setOnClickListener {
            profileViewModel.doLogout()
        }

        btn_update.setOnClickListener {

            val birthdate_txt = input_birthdate.text.toString()
            lateinit var birthdate: Date

            if(birthdate_txt != null) {
                val format = SimpleDateFormat("dd/MM/yyyy");
                birthdate = format.parse(birthdate_txt)
            }

            if (userLogged != null) {
                profileViewModel.updateUser(
                        userLogged,
                        input_firstname.text.toString().capitalize(),
                        input_lastname.text.toString().capitalize(),
                        birthdate,
                        input_phone.text.toString(),
                        input_email.text.toString(),
                        cb_penal.isChecked,
                        cb_trabalhista.isChecked,
                        cb_consumidor.isChecked,
                        cb_tributario.isChecked,
                        cb_imobiliario.isChecked,
                        input_biography.text.toString()
                        )
            }
        }

    }

    private fun setObservers() {

        profileViewModel.logoutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Loading -> showMessage("Efetuando logout... Aguarde")
                is RequestState.Error -> showError(it.throwable)
            }
        })

    }

    private fun showSuccess() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(br.com.creativedevmind.padv.presentation.home.NAVIGATION_KEY)
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

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
