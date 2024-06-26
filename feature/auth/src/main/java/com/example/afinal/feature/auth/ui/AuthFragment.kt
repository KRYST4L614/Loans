package com.example.afinal.feature.auth.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.auth.databinding.FragmentAuthBinding
import com.example.afinal.feature.auth.di.DaggerAuthComponent
import com.example.afinal.feature.auth.presentation.AuthState.Error
import com.example.afinal.feature.auth.presentation.AuthState.Loading
import com.example.afinal.feature.auth.presentation.AuthViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import javax.inject.Inject

class AuthFragment : Fragment() {

    companion object {
        private const val BOTTOM_SHEET_TAG = "BOTTOM_SHEET_TAG"
        fun newInstance() = AuthFragment()
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAuthComponent
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
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        showAuthDialog()

        binding.error.retryButton.setOnClickListener {
            isErrorShows(false)
            showAuthDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> observeLoadingState()
                is Error -> observeErrorState(it)
                else -> {}
            }
        }
    }

    private fun observeLoadingState() {
        isLoadingShows(true)
        isErrorShows(false)
    }

    private fun observeErrorState(error: Error) {
        binding.error.errorMessage.text = error.errorMessage

        isLoadingShows(false)
        isErrorShows(true)
    }

    private fun isErrorShows(shows: Boolean) {
        with(binding) {
            error.root.isVisible = shows
            logo.isVisible = !shows
        }
    }

    private fun isLoadingShows(shows: Boolean) {
        with(binding) {
            logo.isVisible = !shows
            loading.root.isVisible = shows
        }
    }

    private fun showAuthDialog() {
        if (childFragmentManager.findFragmentByTag(BOTTOM_SHEET_TAG)?.isAdded != true) {
            val bs = AuthBottomSheet().apply {
                isCancelable = false
            }
            bs.show(childFragmentManager, BOTTOM_SHEET_TAG)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}