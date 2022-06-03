package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import es.iesnervion.jmmata.blinder.BuildConfig.DEBUG
import es.iesnervion.jmmata.blinder.MainActivity
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.GetUserByIdUseCase
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentPeopleBinding
import java.time.LocalDate
import java.time.Period
import java.util.*

class PeopleFragment : BaseFragment<FragmentPeopleBinding>() {

    private val navController: NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private val args: PeopleFragmentArgs by navArgs() //Argument from another fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility= View.VISIBLE
        if(auth.uid == args.tokenLoged){
            var userObject: UserDBO? = null
            val dataUser = db.collection("Users")
            .document(auth.uid.toString())
            /*Log.d("usiario", dataUser.toString())
            dataUser.get()
            .addOnCompleteListener { doc ->
                userObject = doc.result.toObject<UserDBO>()
            }*/

            var userBO = userObject?.toUserBO() ?: UserBO()
            //setText (userBO)
        }
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
    }

    private fun setText(userLoged: UserBO) {
        binding?.apply {
            peopleLabelName.text = userLoged.name
            peopleLabelCity.text = "Vive en, " + userLoged.city
            peopleLabelAge.text = "27"
            tagGustos.text = userLoged.likes[0]
            tagGustos1.text = userLoged.likes[1]
            tagGustos2.text = userLoged.likes[2]
            peopleLabelDescription.text = userLoged.description
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPeopleBinding = FragmentPeopleBinding.inflate(inflater, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = PeopleFragment()
    }
}