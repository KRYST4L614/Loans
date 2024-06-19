package com.example.afinal.feature.auth.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.auth.databinding.AuthBottomSheetBinding
import com.example.afinal.feature.auth.di.DaggerAuthComponent
import com.example.afinal.feature.auth.presentation.AuthViewModel
import com.example.afinal.feature.auth.util.PasswordTransformation
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class AuthBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AuthBottomSheet()
    }

    private var _binding: AuthBottomSheetBinding? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordEditText.transformationMethod = PasswordTransformation()
        binding.tableLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == 0) {
                        binding.repeatPasswordInputLayout.isVisible = false
                        binding.button.text = "Войти"
                    } else {
                        binding.repeatPasswordInputLayout.isVisible = true
                        binding.button.text = "Зарегистрироваться"
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            }
        )

        binding.button.setOnClickListener {
            viewModel.openOnboarding()
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}