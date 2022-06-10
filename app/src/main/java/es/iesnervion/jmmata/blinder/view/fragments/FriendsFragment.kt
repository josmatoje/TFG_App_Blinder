package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.view.base.BaseFragment
import es.iesnervion.jmmata.blinder.databinding.FragmentFriendsBinding
import es.iesnervion.jmmata.blinder.view.adapters.FriendAdapter
import es.iesnervion.jmmata.blinder.viewmodels.FriendsVM

class FriendsFragment : BaseFragment<FragmentFriendsBinding>() {

    //Variables
    private val navController: NavController by lazy { findNavController() }
    private val auth: FirebaseAuth = Firebase.auth
    private val args: PeopleFragmentArgs by navArgs() //Argument from another fragment
    private val adapter= FriendAdapter(onFriendSelectedListener = { onFriendClicked(it) })
    private val viewModel = FriendsVM()


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
        binding?.friendsFragmentListOfFriends?.adapter = adapter

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVMObservers()
        viewModel.loadFriends()
    }

    //Inflater
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFriendsBinding = FragmentFriendsBinding.inflate(inflater, container, false)

    private fun onFriendClicked (friend: FriendBO){
        friend.id?.let { navController.navigate(FriendsFragmentDirections.navigateToProfielFriendsFragment(it)) }

    }

    //private methods
    private fun setupVMObservers() {
        viewModel.friendsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
