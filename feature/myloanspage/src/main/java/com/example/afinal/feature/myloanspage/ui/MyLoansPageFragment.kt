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
import com.example.afinal.feature.myloanspage.presentation.MyLoansPageViewModel
import com.example.afinal.feature.myloanspage.presentation.UIState
import com.example.afinal.feature.myloanspage.presentation.UIState.Content
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
        binding.toolbar.setNavigationOnClickListener {
            viewModel.close()
        }
        setupLoansList()
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLoans()
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is Content -> {
                    observeContentState(it)
                }
                else -> {}
            }
        }
        viewModel.getLoans()

        requireActivity().onBackPressedDispatcher.addCallback{
            viewModel.close()
            this.remove()
        }
    }

    private fun setupLoansList() {
        with(binding) {
            loans.adapter = LoanItemAdapter(emptyList())
            loans.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeContentState(content: Content) {
        with(binding) {
            (loans.adapter as LoanItemAdapter).updateData(content.data)
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}