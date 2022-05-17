package es.iesnervion.jmmata.blinder.dataaccess.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val navController:NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

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
        }


    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.apply{
                         navController.navigate(LoginFragmentDirections.navigateToPeopleFragment(getIdToken(false).toString()))
                    }
                } else {
                    binding?.errorMessage?.visibility = View.VISIBLE
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
}