package com.example.afinal.feature.myloanspage.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.feature.myloanspage.databinding.FragmentLoansBinding
import com.example.afinal.feature.myloanspage.di.DaggerMyLoansPageComponent
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Content
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Error
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageState.Loading
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.loans.ui.adapter.LoanItemAdapter
import javax.inject.Inject

class MyLoansPageFragment : Fragment() {
    companion object {
        fun newInstance() = MyLoansPageFragment()
    }

    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MyLoansPageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerMyLoansPageComponent
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
        _binding = FragmentLoansBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setupLoansList()
        observeViewModelState()

        viewModel.getLoans()

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.close()
            this.remove()
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.getLoans()
            }
            error.retryButton.setOnClickListener {
                viewModel.getLoans()
            }
        }
    }

    private fun setupLoansList() {
        with(binding) {
            loans.adapter = LoanItemAdapter(emptyList(), viewModel::openLoanDetails)
            loans.layoutManager = LinearLayoutManager(requireContext())
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
            (loans.adapter as LoanItemAdapter).updateData(content.data)
            swipeRefresh.isRefreshing = false
        }

        isErrorShows(false)
        isShimmerStarted(false)
    }

    private fun observeLoadingState() {
        isErrorShows(false)
        isShimmerStarted(true)
    }

    private fun observeErrorState(error: Error) {
        binding.error.errorMessage.text = error.errorMessage
        isErrorShows(true)
        isShimmerStarted(false)
    }

    private fun isShimmerStarted(started: Boolean) {
        with(binding) {
            if (started) {
                loansShimmer.showShimmer(true)
            } else {
                loansShimmer.hideShimmer()
            }
            loansThumbnail.root.isVisible = started
            loans.isVisible = !started
        }
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
        _binding = null
    }
}