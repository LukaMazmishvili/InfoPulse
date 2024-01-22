package com.example.infopulse.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.infopulse.common.inflater
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<VB : ViewBinding>(
    private val inflater: inflater<VB>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun started()
    abstract fun listeners()
    open fun observer() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = this.inflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        started()
        listeners()
        observer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}