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
import com.example.afinal.feature.homepage.presentation.HomePageViewModel
import com.example.afinal.feature.homepage.presentation.UIState.Content
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
        setupSlider()
        setupLoansList()
        setupMenu()

        viewModel.state.observe(viewLifecycleOwner) {
            if (it is Content) {
                observeContentState(it)
            }
        }

        viewModel.getLoans()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLoans()
        }

        binding.myLoansCard.button.setOnClickListener {
            viewModel.openMyLoansPage()
        }
    }

    private fun setupSlider() {
        with(binding) {
            sumSlider.progress = 5
            sumSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        sum.text =
                            getString(ComponentR.string.sum).format(progress * 100).toEditable()
                    }
                    sumMessage.text =
                        if (progress * 100 < 500) getString(R.string.l_bound_sum) else null
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    binding.swipeRefresh.isEnabled = false
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    binding.swipeRefresh.isEnabled = true
                }

            })

            sum.doOnTextChanged { text, _, _, _ ->
                sumSlider.progress = text.toString().dropLast(2).toInt() / 100
                sumMessage.text =
                    if (text.toString().dropLast(2)
                            .toInt() > 10000
                    ) getString(R.string.u_bound_sum) else null
            }
        }
    }

    private fun setupLoansList() {
        with(binding) {
            myLoansCard.loans.adapter = LoanItemAdapter(emptyList())
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

    private fun observeContentState(content: Content) {
        with(binding) {
            if (content.data.isEmpty()) {
                myLoansBody.isVisible = true
                myLoansCard.root.isVisible = false
            } else {
                myLoansCard.root.isVisible = true
                myLoansBody.isVisible = false
                (myLoansCard.loans.adapter as LoanItemAdapter).updateData(content.data)
            }
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}