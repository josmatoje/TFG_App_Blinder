package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
import kotlin.collections.ArrayList

class PeopleFragment : BaseFragment<FragmentPeopleBinding>() {

    //private variables
    private val navController: NavController by lazy { findNavController() }
    private var auth: FirebaseAuth= Firebase.auth
    private val db = Firebase.firestore
    private val args: PeopleFragmentArgs by navArgs() //Argument from another fragment
    private val viewModel: PeopleVM by activityViewModels()

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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVMObservers()
        binding?.peopleMatbtnDislike?.setOnClickListener {
            viewModel.isAMatch(false)
        }
        binding?.peopleMatbtnLike?.setOnClickListener {
            viewModel.isAMatch(true)
        }
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
            setText(it)
        }
    }

    private fun setText(user: UserBO) {
        binding?.apply {

            peopleLabelName.text = user.name
            peopleLabelCity.text = "Vive en, " + user.city
            peopleLabelAge.text = "27" //TODO: poner edad real
            tagGustos.text = if(user.likes.isNotEmpty()) user.likes[0] else ""
            tagGustos1.text = if(user.likes.size > 1) user.likes[1] else ""
            tagGustos2.text = if(user.likes.size > 2) user.likes[2] else ""
            tagGustos3.text = if(user.likes.size > 3) user.likes[3] else ""
            tagGustos4.text = if(user.likes.size > 4) user.likes[4] else ""
            peopleLabelDescription.text = user.description
        }

    }

}