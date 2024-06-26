package com.example.afinal.feature.acceptloan.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.acceptloan.R
import com.example.afinal.feature.acceptloan.databinding.FragmentAcceptBinding
import com.example.afinal.feature.acceptloan.di.DaggerAcceptLoanComponent
import com.example.afinal.feature.acceptloan.presentation.AcceptLoanViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.util.toSum
import javax.inject.Inject

class AcceptLoanFragment : Fragment() {
    companion object {
        private const val SUM_KEY = "SUM_KEY"
        fun newInstance(sum: Int): AcceptLoanFragment {
            val args = Bundle()
            args.putInt(SUM_KEY, sum)
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

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.close()
            this.remove()
        }

        binding.title.text =
            getString(R.string.accept_title).format(requireArguments().getInt(SUM_KEY).toSum())
    }

    private fun setOnClickListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }

            addressesButton.setOnClickListener {
                viewModel.openAddresses()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}