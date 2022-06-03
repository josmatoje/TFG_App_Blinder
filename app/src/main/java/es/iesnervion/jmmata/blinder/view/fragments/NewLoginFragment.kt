package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentNewLoginBinding
import java.text.SimpleDateFormat
import java.util.*

class NewLoginFragment : BaseFragment<FragmentNewLoginBinding>() {

    private val navController:NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var birthDate: Date? = null

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            newLoginEditBirthdate.inputType = InputType.TYPE_NULL
            newLoginEditBirthdate.setOnClickListener {
                val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Seleccione su fecha de nacimiento")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                        .build()
                activity?.let { it1 -> materialDatePicker.show(it1.supportFragmentManager, "matDatePicker") }
                materialDatePicker.addOnPositiveButtonClickListener {
                    birthDate = Date(it)
                    newLoginEditBirthdate.setText(SimpleDateFormat("dd/MM/yyy").format(birthDate))
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
        auth.uid?.let {
            db.collection("Users")
                .document(it)
                .set(UserDTO(
                        it,
                        binding?.newLoginEditUserName?.text.toString(),
                        null,
                        binding?.newLoginEditDescription?.text.toString(),
                        birthDate,
                        GeoPoint(45.7, 6.757),
                        null,
                        null
                    )
                )
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

