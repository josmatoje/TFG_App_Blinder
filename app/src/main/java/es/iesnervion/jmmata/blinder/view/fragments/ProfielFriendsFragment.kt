package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.iesnervion.jmmata.blinder.databinding.FragmentProfielFriendsBinding
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.viewmodels.ProfileFriendsVM

class ProfielFriendsFragment : BaseFragment<FragmentProfielFriendsBinding>() {

    //Variables
    private val navController: NavController by lazy { findNavController() }
    private val args: PeopleFragmentArgs by navArgs()
    private val viewModel: ProfileFriendsVM by viewModels()

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

    }


    //Inflater
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfielFriendsBinding = FragmentProfielFriendsBinding.inflate(inflater, container, false)


}