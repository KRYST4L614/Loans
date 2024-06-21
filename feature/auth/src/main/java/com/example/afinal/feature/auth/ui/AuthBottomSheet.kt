package com.example.afinal.feature.auth.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.auth.databinding.AuthBottomSheetBinding
import com.example.afinal.feature.auth.di.DaggerAuthComponent
import com.example.afinal.feature.auth.presentation.AuthState
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


    private val viewModel by viewModels<AuthViewModel>(ownerProducer = { requireParentFragment() })

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

        with(binding) {
            passwordEditText.transformationMethod = PasswordTransformation()
            tableLayout.addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        if (tab.position == 0) {
                            binding.repeatPasswordInputLayout.isVisible = false
                            binding.button.text = "Войти"
                        } else {
                            binding.repeatPasswordInputLayout.isVisible = true
                            binding.button.text = "Зарегистрироваться"
                        }
                        viewModel.handleTabChange(position = tab.position)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {}
                }
            )

            button.setOnClickListener {
                viewModel.handleButtonClick(
                    binding.loginEditText.text,
                    binding.passwordEditText.text
                )
            }

            loginEditText.doAfterTextChanged {
                viewModel.checkName(it)
            }

            passwordEditText.doAfterTextChanged {
                viewModel.checkPasswords(passwordEditText.text, repeatPasswordEditText.text)
            }

            repeatPasswordEditText.doAfterTextChanged {
                viewModel.checkPasswords(passwordEditText.text, repeatPasswordEditText.text)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            if (it is AuthState.Success) {
                dismiss()
            } else if (it is AuthState.Content) {
                binding.loginInputLayout.error = it.nameErrorMessage
                binding.loginInputLayout.errorIconDrawable = null
                binding.passwordInputLayout.error = it.passwordErrorMessage
                binding.passwordInputLayout.errorIconDrawable = null
                binding.repeatPasswordInputLayout.error = it.repeatedPasswordErrorMessage
                binding.repeatPasswordInputLayout.errorIconDrawable = null
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}