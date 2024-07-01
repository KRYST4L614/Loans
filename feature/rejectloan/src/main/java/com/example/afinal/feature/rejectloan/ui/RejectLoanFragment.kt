package com.example.afinal.feature.rejectloan.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.rejectloan.databinding.FragmentRejectBinding
import com.example.afinal.feature.rejectloan.di.DaggerRejectLoanComponent
import com.example.afinal.feature.rejectloan.presentation.RejectLoanViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import javax.inject.Inject

class RejectLoanFragment : Fragment() {
    companion object {
        fun newInstance() = RejectLoanFragment()
    }

    private var _binding: FragmentRejectBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RejectLoanViewModel> { viewModelFactory }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.close()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerRejectLoanComponent
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
        _binding = FragmentRejectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.close()
        }

        binding.goToHomeButton.setOnClickListener {
            viewModel.close()
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        _binding = null
    }
}