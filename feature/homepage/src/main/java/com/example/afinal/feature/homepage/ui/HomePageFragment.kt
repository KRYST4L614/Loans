package com.example.afinal.feature.homepage.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.feature.homepage.R
import com.example.afinal.feature.homepage.databinding.FragmentHomePageBinding
import com.example.afinal.feature.homepage.di.DaggerHomePageComponent
import com.example.afinal.feature.homepage.presentation.HomePageState.Content
import com.example.afinal.feature.homepage.presentation.HomePageState.Error
import com.example.afinal.feature.homepage.presentation.HomePageState.Loading
import com.example.afinal.feature.homepage.presentation.HomePageViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.loans.ui.adapter.LoanItemAdapter
import com.example.afinal.util.toEditable
import javax.inject.Inject
import com.example.afinal.component.resources.R as ComponentR

class HomePageFragment : Fragment() {
    companion object {
        fun newInstance() = HomePageFragment()
    }

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomePageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerHomePageComponent
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
        _binding = FragmentHomePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setupLoansList()
        setupMenu()

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Error -> observeErrorState(it)
                is Content -> observeContentState(it)
                is Loading -> observeLoadingState()
            }
        }

        viewModel.getLoansData()
    }

    private fun setOnClickListeners() {
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                viewModel.getLoansData()
            }

            myLoansCard.button.setOnClickListener {
                viewModel.openMyLoansPage()
            }

            sliderCard.button.setOnClickListener {
                viewModel.openRequestLoan(sliderCard.sum.text?.toString()?.toInt() ?: 0)
            }

            error.retryButton.setOnClickListener {
                viewModel.getLoansData()
            }
        }
    }

    private fun setupLoansList() {
        with(binding) {
            myLoansCard.loans.adapter = LoanItemAdapter(emptyList(), viewModel::openLoanDetails)
            myLoansCard.loans.layoutManager =
                object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically() = false
                }
        }
    }

    private fun setupMenu() {
        binding.toolbar.menu.findItem(ComponentR.id.info).setOnMenuItemClickListener {
            viewModel.openOnboarding()
            true
        }
    }

    private fun observeErrorState(errorState: Error) {
        with(binding) {
            isErrorShows(true)
            error.errorMessage.text = errorState.errorMessage
        }
    }

    private fun observeContentState(content: Content) {
        with(binding) {
            if (content.loans.isEmpty()) {
                myLoansBody.isVisible = true
                myLoansCard.root.isVisible = false
            } else {
                myLoansCard.root.isVisible = true
                myLoansBody.isVisible = false
                (myLoansCard.loans.adapter as LoanItemAdapter).updateData(content.loans.take(3))
            }

            val conditions = content.conditions

            sliderCard.rangeSumMax.text =
                getString(ComponentR.string.sum).format(conditions.maxAmount)

            setSumTextChangeListener(conditions.maxAmount)

            setupSlider(conditions.maxAmount)

            sliderCard.conditionBody.text = getString(R.string.conditions_body).format(
                conditions.percent,
                conditions.period
            )

            swipeRefresh.isRefreshing = false
            isShimmerStarted(false)

            isErrorShows(false)
        }
    }

    private fun observeLoadingState() {
        isShimmerStarted(true)
        isErrorShows(false)
    }

    private fun isErrorShows(shows: Boolean) {
        with(binding) {
            swipeRefresh.isRefreshing = false
            error.root.isVisible = shows
            scrollView.isVisible = !shows
        }
    }

    private fun isShimmerStarted(started: Boolean) {
        with(binding) {
            if (started) {
                sliderCardShimmer.showShimmer(true)
                myLoansCardShimmer.showShimmer(true)
            } else {
                sliderCardShimmer.hideShimmer()
                myLoansCardShimmer.hideShimmer()
            }

            sliderCard.sliderCardThumbnail.root.isVisible = started
            sliderCard.sliderCardConsLayout.isVisible = !started

            myLoansCard.myLoansCardThumbnail.root.isVisible = started
            myLoansCard.myLoansCardLinLayout.isVisible = !started
        }
    }

    private fun setSumTextChangeListener(maxAmount: Int) {
        with(binding) {
            sliderCard.sum.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty()) {
                    val sum = text.toString().toInt()
                    sliderCard.sumSlider.progress = if (sum > 0) sum / (maxAmount / 100) else 0
                    sliderCard.sumMessage.text = viewModel.checkAmountIsValid(sum)
                }
            }
        }
    }

    private fun setupSlider(maxAmount: Int) {
        with(binding) {
            sliderCard.sumSlider.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val amount = (maxAmount * progress / 100.0).toInt()
                    if (fromUser) {
                        sliderCard.sum.text = amount.toString().toEditable()
                    }
                    sliderCard.sumMessage.text =
                        if (amount < 500) getString(R.string.l_bound_sum) else null
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    binding.swipeRefresh.isEnabled = false
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    binding.swipeRefresh.isEnabled = true
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}