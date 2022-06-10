package es.iesnervion.jmmata.blinder.view.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.MainActivity
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.dbo.UserDBO
import es.iesnervion.jmmata.blinder.dataaccess.local.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserBO
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentPeopleBinding
import es.iesnervion.jmmata.blinder.viewmodels.PeopleVM
import java.util.*
import kotlin.collections.ArrayList

class PeopleFragment : BaseFragment<FragmentPeopleBinding>() {

    //private variables
    private val navController: NavController by lazy { findNavController() }
    private var auth: FirebaseAuth= Firebase.auth
    private val db = Firebase.firestore
    private val args: PeopleFragmentArgs by navArgs() //Argument from another fragment
    private val viewModel: PeopleVM by viewModels()

    //life cycle functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflateViewBinding(inflater, container)
        binding?.peopleMatbtnDislike?.setOnClickListener {
            viewModel.isAMatch(false)
        }
        binding?.peopleMatbtnLike?.setOnClickListener {
            viewModel.isAMatch(true)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVMObservers()
        viewModel.loadUser()
    }

    override fun onStart() {
        super.onStart()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility= View.VISIBLE
    }

    //Inflater
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPeopleBinding = FragmentPeopleBinding.inflate(inflater, container, false)

    //private methods
    private fun setupVMObservers() {
        viewModel.userPresented.observe(viewLifecycleOwner) {
            if(it.equals(UserBO()))
                noMoreUsers()
            else
                setText(it)
        }
    }

    /*Actualiza los labels con los datos del usuario que se quiere mostrar y actualiza la visibilidad de los tags para que muestre los necesarios */
    private fun setText(user: UserBO) {
        binding?.apply {
            val tags: List<TextView> = listOf(tagGustos, tagGustos1, tagGustos2, tagGustos3, tagGustos4)
            var i = 0
            peopleMainLayout.visibility = View.VISIBLE
            peopleLabelName.text = user.name
            peopleLabelCity.text = "Vive en, " + user.city
            peopleLabelAge.text = getYears(user.birthdate).toString()+" años"
            while (i < tags.size ){
                if(user.likes.isNotEmpty() &&  i < user.likes.size ) {
                    tags[i].visibility = View.VISIBLE
                    tags[i].text = user.likes[i]
                } else {
                    tags[i].visibility = View.GONE
                }
                i++
            }
            peopleLabelDescription.text = user.description
            friendSeparatorEmpty.visibility = View.GONE
            peopleEmpty.visibility = View.GONE
        }
    }
    /*Oculta los placeholders de los labels en el caso de que no haya más usuarios que mostrar*/
    private fun noMoreUsers() {
        binding?.apply {
            peopleMainLayout.visibility = View.GONE
            friendSeparatorEmpty.visibility = View.VISIBLE
            peopleEmpty.visibility = View.VISIBLE
        }
    }

    /*Metodo que calcula la edad en función de una fecha de nacimiento*/
    private fun getYears(birthdate: Date): Int {
        var years = Date().year - birthdate.year
        if(Date().month < birthdate.month ||
            Date().month == birthdate.month && Date().day < birthdate.day){
            years--
        }
        return years
    }

}