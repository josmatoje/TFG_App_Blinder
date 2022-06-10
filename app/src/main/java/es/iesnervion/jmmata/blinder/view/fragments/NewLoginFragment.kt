package es.iesnervion.jmmata.blinder.view.fragments

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserDTO
import es.iesnervion.jmmata.blinder.databinding.FragmentNewLoginBinding
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.viewmodels.NewLoginVM
import java.text.SimpleDateFormat
import java.util.*


class NewLoginFragment : BaseFragment<FragmentNewLoginBinding>() {

    //Variables
    private val navController:NavController by lazy { findNavController() }
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private var birthDate: Date? = null
    private val viewModel: NewLoginVM by viewModels()
    /*private var adapter = ArrayAdapter.createFromResource(
                                    requireContext(),
                                    R.array.genders,
                                    R.layout.dropdown_list
                                    )*/
                //.setDropDownViewResource(R.layout.dropdown_list)

    //Life cycle functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            //newLoginSpinerGender.setAdapter(adapter)
            newLoginButtonConfirm.setOnClickListener{
                //Un for que recorra un listado de los componentes y marque un error en el textfield para cada campo q esté sin rellenar
                if (newLoginEditUserName.text?.isNotBlank() == true &&
                    newLoginEditEmail.text?.isNotBlank() == true &&
                    newLoginEditPassword.text?.isNotBlank() == true &&
                    newLoginEditRepeatPassword.text?.isNotBlank() == true &&
                    newLoginEditBirthdate.text?.isNotBlank() == true) { //Recomended by android (Bolean? could have three values)
                    if(newLoginEditPassword.text?.length ?: 0 < 6){
                        newLoginEditPassword.error = "La contraseña debe de ser de al menos 6 caracteres."
                    }
                    else if(newLoginEditPassword.text.toString().equals(newLoginEditRepeatPassword.text.toString())){
                        context?.let { context ->
                            viewModel.createUserAuth(newLoginEditEmail.text.toString(),
                                newLoginEditPassword.text.toString(),
                                context, getUserObject())
                            navController.navigate(R.id.navigateToBaseLogin)
                        }
                    } else {
                        newLoginEditRepeatPassword.error = "Las contraseñas no son iguales."
                        newLoginEditPassword.error = "Las contraseñas no son iguales."
                        //Toast.makeText(context, "Las contraseñas no son iguales." , Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Hay campos sin rellenar" , Toast.LENGTH_LONG).show()
                }
            }
        }
    }

/*    //TODO: delete this
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "ERROR de autentificación.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        auth.uid?.let {
            db.collection("Users")
                .document(it)
                .set(
                )
            }
    }*/

    //Inflatter function
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewLoginBinding = FragmentNewLoginBinding.inflate(inflater, container, false)

    //Private function
    private fun getUserObject(): UserBO =
        UserBO(
            auth.uid,
            binding?.newLoginEditUserName?.text.toString(),
            binding?.newLoginEditCity?.text.toString(),
            binding?.newLoginEditDescription?.text.toString(),
            birthDate ?: Date(),
            //hashMapOf(Pair("geohash", GeoFireUtils.getGeoHashForLocation(GeoLocation(45.7, 6.757))),Pair("lat", 45.7),Pair("lng", 6.757)),
            Location(""),
            emptyList(),
            binding?.newLoginSpinerGender?.text.toString(),
            binding?.newLoginSpinerSexuality?.text.toString(),
        )


}


