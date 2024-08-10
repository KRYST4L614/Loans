package com.example.afinal.feature.loandetails.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.loandetails.R
import com.example.afinal.feature.loandetails.databinding.FragmentLoanDetailsBinding
import com.example.afinal.feature.loandetails.di.DaggerLoanDetailsComponent
import com.example.afinal.feature.loandetails.presentation.LoanDetailsState.Content
import com.example.afinal.feature.loandetails.presentation.LoanDetailsState.Error
import com.example.afinal.feature.loandetails.presentation.LoanDetailsState.Loading
import com.example.afinal.feature.loandetails.presentation.LoanDetailsViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.Status
import com.example.afinal.util.convertToString
import com.example.afinal.util.toSum
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

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.close()
            }
        }

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
            requireArguments().getParcelable(LOAN_KEY, Loan::class.java)
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(LOAN_KEY)
        }

        loan?.let {
            setupLoanView(it)
            setOnClickListeners(it)
            isShimmerStarted(false)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Content -> observeContentState(it)
                is Loading -> observeLoadingState()
                is Error -> observeErrorState(it)
            }
        }
    }

    private fun observeContentState(content: Content) {
        setupLoanView(content.data)
        binding.swipeRefresh.isRefreshing = false
        isShimmerStarted(false)
        isErrorShows(false)
    }

    private fun setupLoanView(loan: Loan) {
        with(binding) {
            setupUserInfoCard(loan)
            setupLoanDetailsCard(loan)
            setupStatusCard(loan.status)
            binding.toolbar.title = root.context.getString(LoansR.string.loan_id).format(loan.id)
        }
    }

    private fun setupUserInfoCard(loan: Loan) {
        with(binding.userInfoCard) {
            name.text = loan.firstName
            secondName.text = loan.lastName
            phone.text = loan.phoneNumber
        }
    }

    private fun setupLoanDetailsCard(loan: Loan) {
        with(binding.loanDetailsCard) {
            loanId.text = getString(LoansR.string.loan_id).format(loan.id)
            date.text = loan.date.convertToString(
                "d.MM.y",
                resources.configuration.locales[0]
            )
            percent.text = getString(R.string.percent).format(loan.percent)
            period.text = loan.period.toString()
            sum.text = getString(ComponentR.string.sum).format(
                loan.amount.toSum()
            )
        }
    }

    private fun setupStatusCard(status: Status) {
        with(binding.statusCard.status) {
            text = viewModel.chooseStatusText(status)
            setTextColor(viewModel.chooseStatusColor(status))
        }
    }

    private fun observeLoadingState() {
        isShimmerStarted(true)
        isErrorShows(false)
    }

    private fun observeErrorState(error: Error) {
        binding.error.errorMessage.text = error.errorMessage
        isShimmerStarted(false)
        isErrorShows(true)
    }

    private fun isShimmerStarted(started: Boolean) {
        with(binding) {
            if (started) {
                userInfoCardShimmer.showShimmer(true)
                loanDetailsCardShimmer.showShimmer(true)
                statusCardShimmer.showShimmer(true)
            } else {
                userInfoCardShimmer.hideShimmer()
                loanDetailsCardShimmer.hideShimmer()
                statusCardShimmer.hideShimmer()
            }
            userInfoCard.userInfoCardThumbnail.root.isVisible = started
            userInfoCard.userInfoConstLayout.isVisible = !started

            loanDetailsCard.loanDetailsCardThumbnail.root.isVisible = started
            loanDetailsCard.loanDetailsConstLayout.isVisible = !started

            statusCard.statusCardThumbnail.root.isVisible = started
            statusCard.statusCardConstLayout.isVisible = !started
        }
    }

    private fun setOnClickListeners(loan: Loan) {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }

            error.retryButton.setOnClickListener {
                viewModel.getLoanDetails(loan.id)
            }

            binding.swipeRefresh.setOnRefreshListener {
                viewModel.getLoanDetails(loan.id)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun isErrorShows(shows: Boolean) {
        with(binding) {
            swipeRefresh.isRefreshing = false
            error.root.isVisible = shows
            swipeRefresh.isVisible = !shows
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        _binding = null
    }
}