package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.databinding.FragmentChatFriendBinding
import es.iesnervion.jmmata.blinder.databinding.FragmentFriendsBinding
import es.iesnervion.jmmata.blinder.view.base.BaseFragment


class ChatFriendFragment : BaseFragment<FragmentChatFriendBinding>() {


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


    //Inflater
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatFriendBinding = FragmentChatFriendBinding.inflate(inflater, container, false)

}