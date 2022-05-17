package es.iesnervion.jmmata.blinder.dataaccess.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentNewLoginBinding
import java.util.*

class NewLoginFragment : BaseFragment<FragmentNewLoginBinding>() {

    private val navController:NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflateViewBinding(inflater, container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            newLoginEditBirthdate.inputType = InputType.TYPE_NULL
            newLoginEditBirthdate.setOnClickListener {
                val calendar = Calendar.getInstance()
//            val datePicker =
//                MaterialStyledDatePickerDialog(this.context, DatePickerDialog.OnDateSetListener(){
//                    onDateSet()
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),)

                val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                    .addOnPositiveButtonClickListener {
                        newLoginEditBirthdate.setText("8/9/1995")
                    }
            }
            newLoginButtonConfirm.setOnClickListener{
                if (newLoginEditEmail.text?.isNotBlank() == true && newLoginEditPassword.text?.isNotBlank() == true) { //Recomended by android (Bolean? could have three values)
                    createAccount(newLoginEditEmail.text.toString(), newLoginEditPassword.text.toString())
                    Toast.makeText(context, "Usuario registrado" , Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Ha abandonado el registro" , Toast.LENGTH_LONG).show()
                }
                navController.navigate(R.id.navigateToBaseLogin)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "ERROR de autentificación.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                // Email Verification sent
            }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewLoginBinding = FragmentNewLoginBinding.inflate(inflater, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = NewLoginFragment()
    }

}

