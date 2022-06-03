package es.iesnervion.jmmata.blinder.view.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<TypeBinding : ViewBinding> : Fragment() {

    protected var binding: TypeBinding? = null

    protected abstract fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): TypeBinding

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}