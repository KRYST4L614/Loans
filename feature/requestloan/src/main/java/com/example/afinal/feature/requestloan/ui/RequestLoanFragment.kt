package com.example.afinal.feature.requestloan.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.requestloan.databinding.FragmentRequestLoanBinding
import com.example.afinal.feature.requestloan.di.DaggerRequestLoanComponent
import com.example.afinal.feature.requestloan.presentation.RequestLoanState.Content
import com.example.afinal.feature.requestloan.presentation.RequestLoanState.Error
import com.example.afinal.feature.requestloan.presentation.RequestLoanState.Loading
import com.example.afinal.feature.requestloan.presentation.RequestLoanViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.loans.domain.entities.LoanConditions
import javax.inject.Inject

class RequestLoanFragment : Fragment() {
    companion object {
        private const val LOAN_CONDITIONS_KEY = "SUM_KEY"
        fun newInstance(loanConditions: LoanConditions): RequestLoanFragment {
            val args = Bundle()
            args.putParcelable(LOAN_CONDITIONS_KEY, loanConditions)
            return RequestLoanFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: FragmentRequestLoanBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RequestLoanViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerRequestLoanComponent
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
        _binding = FragmentRequestLoanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setTextChangeListeners()

        observeViewModelState()
    }

    private fun setOnClickListeners() {
        setRequestLoanButtonClickListener()

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.close()
            this.remove()
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }

            error.retryButton.setOnClickListener {
                error.root.isVisible = false
                contentGroup.isVisible = true
            }
        }
    }

    private fun setRequestLoanButtonClickListener() {
        with(binding) {
            requestLoanButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    viewModel.requestLoan(
                        nameEditText.text?.toString() ?: "",
                        secondNameEditText.text?.toString() ?: "",
                        phoneEditText.text?.toString() ?: "",
                        loanConditions = arguments?.getParcelable(
                            LOAN_CONDITIONS_KEY,
                            LoanConditions::class.java
                        )
                    )
                } else {
                    @Suppress("DEPRECATION")
                    viewModel.requestLoan(
                        nameEditText.text?.toString() ?: "",
                        secondNameEditText.text?.toString() ?: "",
                        phoneEditText.text?.toString() ?: "",
                        loanConditions = arguments?.getParcelable(
                            LOAN_CONDITIONS_KEY
                        )
                    )
                }
            }
        }
    }

    private fun setTextChangeListeners() {
        with(binding) {
            phoneEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())

            phoneEditText.doAfterTextChanged {
                viewModel.checkPhone(it?.toString() ?: "")
            }

            nameEditText.doAfterTextChanged {
                viewModel.checkFirstName(it?.toString() ?: "")
            }

            secondNameEditText.doAfterTextChanged {
                viewModel.checkLastName(it?.toString() ?: "")
            }
        }
    }

    private fun observeViewModelState() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Content -> observeContentState(it)
                is Loading -> observeLoadingState()
                is Error -> observeErrorState(it)
            }
        }
    }

    private fun observeContentState(content: Content) {
        with(binding) {
            progressBar.isVisible = false
            contentGroup.isVisible = true
            error.root.isVisible = false

            phoneInputLayout.error = content.phoneNumberError
            nameInputLayout.error = content.firstNameError
            secondNameInputLayout.error = content.lastNameError
        }
    }

    private fun observeLoadingState() {
        with(binding) {
            progressBar.isVisible = true
            contentGroup.isVisible = false

            error.root.isVisible = false
        }
    }

    private fun observeErrorState(errorState: Error) {
        with(binding) {
            error.errorMessage.text = errorState.errorMessage
            error.root.isVisible = true

            progressBar.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}