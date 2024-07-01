package com.example.afinal.feature.auth.ui

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.afinal.feature.auth.R
import com.example.afinal.feature.auth.databinding.AuthBottomSheetBinding
import com.example.afinal.feature.auth.presentation.AuthState
import com.example.afinal.feature.auth.presentation.AuthState.Content
import com.example.afinal.feature.auth.presentation.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout

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

        setOnClickListeners()
        setTextChangeListeners()
        setupTabLayout()

        observeViewModelState()

    }

    private fun setTextChangeListeners() {
        with(binding) {
            passwordEditText.transformationMethod = PasswordTransformationMethod()

            loginEditText.doAfterTextChanged {
                viewModel.checkName(it?.toString() ?: "")
            }

            passwordEditText.doAfterTextChanged {
                viewModel.checkPasswords(
                    passwordEditText.text?.toString() ?: "",
                    repeatPasswordEditText.text?.toString() ?: ""
                )
            }

            repeatPasswordEditText.doAfterTextChanged {
                viewModel.checkPasswords(
                    passwordEditText.text?.toString() ?: "",
                    repeatPasswordEditText.text?.toString() ?: ""
                )
            }
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            button.setOnClickListener {
                viewModel.handleButtonClick(
                    loginEditText.text?.toString() ?: "",
                    passwordEditText.text?.toString() ?: "",
                    repeatPasswordEditText.text?.toString() ?: ""

                )
            }
        }
    }

    private fun setupTabLayout() {
        with(binding) {

            tableLayout.addOnTabSelectedListener(

                object : TabLayout.OnTabSelectedListener {

                    override fun onTabSelected(tab: TabLayout.Tab) {

                        if (tab.position == 0) {
                            repeatPasswordInputLayout.isVisible = false
                            button.text = getString(R.string.enter)
                        } else {
                            repeatPasswordInputLayout.isVisible = true
                            button.text = getString(R.string.register)
                        }

                        viewModel.handleTabChange(position = tab.position)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {}
                }
            )
        }
    }

    private fun observeViewModelState() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Content -> observeContentState(it)

                is AuthState.Loading -> dismiss()

                else -> {}
            }
        }
    }

    private fun observeContentState(content: Content) {
        with(binding) {
            loginInputLayout.error = content.nameErrorMessage
            passwordInputLayout.error = content.passwordErrorMessage
            repeatPasswordInputLayout.error = content.repeatedPasswordErrorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.close()
    }

}