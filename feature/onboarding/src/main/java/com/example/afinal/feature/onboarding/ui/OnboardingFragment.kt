package com.example.afinal.feature.onboarding.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.onboarding.R
import com.example.afinal.feature.onboarding.databinding.FragmentOnboardingBinding
import com.example.afinal.feature.onboarding.di.DaggerOnboardingComponent
import com.example.afinal.feature.onboarding.presentation.OnboardingViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.viewpageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class OnboardingFragment : Fragment() {
    companion object {
        fun newInstance() = OnboardingFragment()
    }

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<OnboardingViewModel> { viewModelFactory }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.openHome()
            }
        }

    override fun onAttach(context: Context) {
        DaggerOnboardingComponent
            .builder()
            .dependencies(FragmentDependenciesStore.dependencies)
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        setOnClickListeners()

        setTabChangeListener()
    }

    private fun setupViewPager() {
        with(binding) {
            viewPager.adapter = ViewPagerAdapter(
                childFragmentManager,
                lifecycle,
                listOf(
                    ArrangeLoanOnboardingFragment::newInstance,
                    GetLoanOnboardingFragment::newInstance,
                    ArrangedLoansOnboardingFragment::newInstance
                )
            )
            TabLayoutMediator(tableLayout, viewPager) { _, _ ->
            }.attach()
        }
    }

    private fun setOnClickListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        with(binding) {
            backButton.setOnClickListener {
                viewPager.currentItem = (viewPager.currentItem - 1).coerceAtLeast(0)
            }

            nextButton.setOnClickListener {
                if (viewPager.currentItem == 2) {
                    viewModel.openHome()
                }
                viewPager.currentItem = (viewPager.currentItem + 1).coerceAtMost(2)
            }

            toolbar.setNavigationOnClickListener {
                viewModel.openHome()
            }
        }
    }

    private fun setTabChangeListener() {
        with(binding) {
            tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            backButton.isVisible = false
                            nextButton.text = getString(R.string.next_button)
                        }

                        2 -> {
                            nextButton.text = getString(R.string.close_button)
                            backButton.isVisible = true
                        }

                        else -> {
                            backButton.isVisible = true
                            nextButton.text = getString(R.string.next_button)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        _binding = null
    }
}