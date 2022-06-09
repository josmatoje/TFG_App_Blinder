package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import es.iesnervion.jmmata.blinder.MainActivity
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentLoginBinding
import es.iesnervion.jmmata.blinder.viewmodels.LoginVM

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val navController:NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateViewBinding(inflater, container)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility= View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            loginMatButNewLogin.setOnClickListener{ navController.navigate(R.id.navigateToNewLogin) }

            loginMatButUser.setOnClickListener {
                // if(binding?.etName?.text?.is?NotBlank():false)
                if (etName.text?.isNotBlank() == true && etPassword.text?.isNotBlank() == true) { //Recomended by android (Bolean? could have three values)
                    signIn(etName.text.toString(), etPassword.text.toString())
                } else {
                    if (etName.text?.isNotBlank() == true){

                    }
                }
            }
            forgetPassword.setOnClickListener {
                binding?.etName?.text.toString().let {
                    if(it.isEmpty()){
                        Toast.makeText(context, "Introduzca una dirección de correo", Toast.LENGTH_SHORT).show()
                    }else {
                        forgotPassword(it)
                    }
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.uid?.let {
                        //viewModel.loadLocalDatasFrom()
                        navController.navigate(LoginFragmentDirections.navigateToPeopleFragment(it))
                    }
                } else {
                    binding?.errorMessage?.visibility = View.VISIBLE
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun forgotPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Correo enviado, compruebe su bandeja de entrada.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "El correo no se encuentra en la base de datos", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener {
                Toast.makeText(context, "Correo enviado, compruebe su bandeja de entrada.", Toast.LENGTH_LONG).show()
            }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
}