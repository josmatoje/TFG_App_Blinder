package es.iesnervion.jmmata.blinder.dataaccess.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentPeopleBinding

class PeopleFragment : BaseFragment<FragmentPeopleBinding>() {

    private val navController: NavController by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth
    private val args: PeopleFragmentArgs by navArgs() //Argument from another fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser.toString() == args.tokenLoged){ //TODO: ESTA MAL
            //reload();
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

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPeopleBinding = FragmentPeopleBinding.inflate(inflater, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = PeopleFragment()
    }
}