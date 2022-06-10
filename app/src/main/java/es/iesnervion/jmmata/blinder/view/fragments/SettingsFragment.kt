package es.iesnervion.jmmata.blinder.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.databinding.FragmentSettingsBinding
import es.iesnervion.jmmata.blinder.view.base.BaseFragment


class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        TODO("Not yet implemented")
    }

}