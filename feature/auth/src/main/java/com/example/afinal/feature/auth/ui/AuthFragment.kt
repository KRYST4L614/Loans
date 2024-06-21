package com.example.afinal.feature.auth.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.auth.databinding.FragmentAuthBinding
import com.example.afinal.feature.auth.di.DaggerAuthComponent
import com.example.afinal.feature.auth.presentation.AuthState
import com.example.afinal.feature.auth.presentation.AuthViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import javax.inject.Inject

class AuthFragment : Fragment() {

    companion object {
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
        with(binding) {
            progress.isVisible = false
            logo.updatePadding(
                bottom = (500 * requireContext().resources.displayMetrics.density).toInt(),
                top = (136 * requireContext().resources.displayMetrics.density).toInt()
            )
        }
        viewModel.state.observe(this) {

        }
        showAuthDialog()
    }

    private fun showAuthDialog() {
        if (childFragmentManager.findFragmentByTag("")?.isAdded != true) {
            val bs = AuthBottomSheet().apply {
                isCancelable = false
            }
            bs.show(childFragmentManager, "")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}