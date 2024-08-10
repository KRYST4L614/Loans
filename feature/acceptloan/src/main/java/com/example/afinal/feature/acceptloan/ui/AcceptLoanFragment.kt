package com.example.afinal.feature.acceptloan.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.acceptloan.R
import com.example.afinal.feature.acceptloan.databinding.FragmentAcceptBinding
import com.example.afinal.feature.acceptloan.di.DaggerAcceptLoanComponent
import com.example.afinal.feature.acceptloan.presentation.AcceptLoanViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.util.convertToString
import com.example.afinal.util.toSum
import java.util.Date
import javax.inject.Inject

class AcceptLoanFragment : Fragment() {
    companion object {
        private const val SUM_KEY = "SUM_KEY"
        private const val ISSUE_DATE = "ISSUE_DATE_KEY"
        fun newInstance(sum: Int, issueDate: Date): AcceptLoanFragment {
            val args = Bundle()
            args.putInt(SUM_KEY, sum)
            args.putSerializable(ISSUE_DATE, issueDate)
            return AcceptLoanFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: FragmentAcceptBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AcceptLoanViewModel> { viewModelFactory }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.close()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAcceptLoanComponent
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
        _binding = FragmentAcceptBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        with(binding) {
            title.text =
                getString(R.string.accept_title).format(requireArguments().getInt(SUM_KEY).toSum())
            body.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getString(R.string.accept_body).format(
                    requireArguments().getSerializable(
                        ISSUE_DATE,
                        Date::class.java
                    )?.convertToString("d MMMM", resources.configuration.locales[0])
                )
            } else {
                getString(R.string.accept_body).format(
                    (requireArguments().getSerializable(
                        ISSUE_DATE
                    ) as Date).convertToString("d MMMM", resources.configuration.locales[0])
                )
            }
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }

            addressesButton.setOnClickListener {
                viewModel.openAddresses()
            }

            requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        _binding = null
    }
}