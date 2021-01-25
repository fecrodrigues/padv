package br.com.creativedevmind.padv.presentation.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.creativedevmind.padv.R
import br.com.creativedevmind.padv.data.remote.datasource.firebase.UserRemoteFirebaseDataSourceImpl
import br.com.creativedevmind.padv.data.repository.UserRepositoryImpl
import br.com.creativedevmind.padv.domain.entity.User
import br.com.creativedevmind.padv.domain.entity.util.RequestState
import br.com.creativedevmind.padv.domain.usecase.LogoutUseCase
import br.com.creativedevmind.padv.presentation.base.auth.BaseAuthFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private val db = FirebaseFirestore.getInstance()
    private val userLogged = FirebaseAuth.getInstance().currentUser?.uid

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
    private lateinit var btn_search: Button
    private lateinit var input_search: EditText
    private lateinit var result_list: RecyclerView
    private lateinit var logged_user_firstName: TextView
    private lateinit var logged_user_lastName: TextView
    private lateinit var image_user_avatar: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpVariables(view)
        setListeners()
        setObservers()

    }

    private fun setUpVariables(view: View) {
        btn_logout = view.findViewById(R.id.btn_logout)
        btn_search = view.findViewById(R.id.btn_search)
        input_search = view.findViewById(R.id.input_search)
        result_list = view.findViewById(R.id.result_list)
        logged_user_firstName = view.findViewById(R.id.logged_user_firstName)
        logged_user_lastName = view.findViewById(R.id.logged_user_lastName)
        image_user_avatar = view.findViewById(R.id.image_user_avatar)


/*        if (user != null) {
            val uid = user.uid
            val userData = db.collection("users").whereEqualTo("id", uid).limit(1)
            userData?.get()?.addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(User::class.java)
                logged_user_firstName.setText(user.get().)
            }
        }*/
        val docRef = userLogged?.let { db.collection("users").document(it) }
        if (docRef != null) {
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("exist", "DocumentSnapshot data: ${document.data} = User: ${userLogged}")
                    logged_user_firstName.text = document.getString("firstName")
                    logged_user_lastName.text = document.getString("lastName")
                    var advocateAccount = document.getBoolean("advocate")
                    if (advocateAccount == true) {
                        input_search.visibility = View.INVISIBLE
                        btn_search.visibility = View.INVISIBLE
                        result_list.visibility = View.INVISIBLE
                    }
                } else {
                    Log.d("noexist", "No such document")
                }
            }
                .addOnFailureListener { exception ->
                    Log.d("errordb","get failed with", exception)
                }
        }




//        logged_user_firstName.setText("Sheldon")
//        logged_user_lastName.setText("Vieira")


    }

/*    private fun firebaseCurrentlyUser(uid: FirebaseUser?) {

        val query = db.collection("users").document(uid.toString())
        query.get().addOnSuccessListener {
            println("Nome: ${it.get("firstName")}")

        }
    }*/

    private fun setListeners() {

        btn_logout.setOnClickListener {
            homeViewModel.doLogout()
        }
        btn_search.setOnClickListener {
            println("Button Clicked")
            firebaseUserSearch(input_search.text.toString());
        }
        image_user_avatar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }

    private fun firebaseUserSearch(username: String) {
        val query = db.collection("users").whereGreaterThanOrEqualTo("firstName", username.capitalize())
            .whereLessThan("firstName", username.capitalize()+"z").whereEqualTo("advocate", true)
        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<User, UserViewHolder> (options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(this@HomeFragment.context).inflate(R.layout.users_list, parent, false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                val tvFirstName: TextView = holder.itemView.findViewById(R.id.db_firstName)
                val tvLastName: TextView = holder.itemView.findViewById(R.id.db_lastName)
                val tvDescription: TextView = holder.itemView.findViewById(R.id.db_description)
                tvFirstName.text = model.firstName
                tvLastName.text = model.lastName
                tvDescription.text = model.lawyerInfo.biography
            }
        }
        result_list.adapter = adapter
        result_list.layoutManager = LinearLayoutManager(this.context)
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

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
