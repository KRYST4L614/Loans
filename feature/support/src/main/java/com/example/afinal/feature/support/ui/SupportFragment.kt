package com.example.afinal.feature.support.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.support.databinding.FragmentSupportBinding
import com.example.afinal.feature.support.di.DaggerSupportComponent
import com.example.afinal.feature.support.presentation.SupportViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import javax.inject.Inject

class SupportFragment : Fragment() {
    companion object {
        fun newInstance() = SupportFragment()
    }

    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<SupportViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSupportComponent
            .builder()
            .dependencies(FragmentDependenciesStore.dependencies)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.close()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.close()
            this.remove()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}