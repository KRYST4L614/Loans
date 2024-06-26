package com.example.afinal.feature.onboarding.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
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
        setMenuVisibility(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.openHome()
        }
        binding.viewPager.adapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            listOf(
                ArrangeLoanOnboardingFragment::newInstance,
                GetLoanOnboardingFragment::newInstance,
                ArrangedLoansOnboardingFragment::newInstance
            )
        )
        TabLayoutMediator(binding.tableLayout, binding.viewPager) { _, _ ->
        }.attach()

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
        }

        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.backButton.isVisible = false

                    2 -> binding.nextButton.text = getString(R.string.close_button)

                    else -> {
                        binding.backButton.isVisible = true
                        binding.nextButton.text = getString(R.string.next_button)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        _binding = null
    }
}