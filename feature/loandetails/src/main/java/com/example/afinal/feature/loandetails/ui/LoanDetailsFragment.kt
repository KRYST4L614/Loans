package com.example.afinal.feature.loandetails.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.loandetails.R
import com.example.afinal.feature.loandetails.databinding.FragmentLoanDetailsBinding
import com.example.afinal.feature.loandetails.di.DaggerLoanDetailsComponent
import com.example.afinal.feature.loandetails.presentation.LoanDetailsState
import com.example.afinal.feature.loandetails.presentation.LoanDetailsViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.Status
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import com.example.afinal.component.resources.R as ComponentR
import com.example.afinal.shared.loans.R as LoansR

class LoanDetailsFragment : Fragment() {
    companion object {
        private const val LOAN_KEY = "LOAN_KEY"
        fun newInstance(loan: Loan): LoanDetailsFragment {
            val args = Bundle()
            args.putParcelable(LOAN_KEY, loan)
            return LoanDetailsFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: FragmentLoanDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LoanDetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerLoanDetailsComponent
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
        _binding = FragmentLoanDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loan: Loan? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOAN_KEY, Loan::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(LOAN_KEY)
        }
        loan?.let {
            setupLoanView(it)
        }

        binding.swipeRefresh.setOnRefreshListener {
            loan?.let {
                viewModel.getLoanDetails(it.id)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            if (it is LoanDetailsState.Content) {
                setupLoanView(it.data)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            viewModel.close()
        }
    }

    private fun setupLoanView(loan: Loan) {
        with(binding) {
            name.text = loan.firstName
            secondName.text = loan.lastName
            phone.text = loan.phoneNumber
            loanId.text = root.context.getString(LoansR.string.loan_id).format(loan.id)
            date.text = SimpleDateFormat(
                "d MMMM, EEE",
                resources.configuration.locales[0]
            ).format(loan.date)
            percent.text = getString(R.string.percent).format(loan.percent)
            period.text = loan.period.toString()

            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols

            symbols.setGroupingSeparator(' ')
            formatter.decimalFormatSymbols = symbols

            sum.text = getString(com.example.afinal.component.resources.R.string.sum).format(
                formatter.format(loan.amount)
            )
            statusId.text = when (loan.state) {
                Status.APPROVED -> {
                    statusId.setTextColor(requireContext().getColor(LoansR.color.indicator_positive))
                    root.context.getString(LoansR.string.loan_approved)
                }

                Status.REGISTERED -> {
                    statusId.setTextColor(requireContext().getColor(LoansR.color.indicator_attention))
                    root.context.getString(LoansR.string.loan_registered)
                }

                Status.REJECTED -> {
                    statusId.setTextColor(requireContext().getColor(ComponentR.color.onSecondary))
                    root.context.getString(LoansR.string.loan_rejected)
                }
            }
            binding.toolbar.title = root.context.getString(LoansR.string.loan_id).format(loan.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}