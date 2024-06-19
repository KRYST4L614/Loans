package com.example.afinal.feature.onboarding.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.onboarding.databinding.FragmentOnboardingBinding
import com.example.afinal.feature.onboarding.di.DaggerOnboardingComponent
import com.example.afinal.feature.onboarding.presentation.OnboardingViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.example.afinal.shared.viewPagerAdapter.ViewPagerAdapter
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
            viewModel.openHomePage()
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
                    viewModel.openHomePage()
                }
                viewPager.currentItem = (viewPager.currentItem + 1).coerceAtMost(2)
            }
        }

        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.backButton.visibility = View.INVISIBLE
                    2 -> binding.nextButton.text = "Закрыть"
                    else -> {
                        binding.backButton.visibility = View.VISIBLE
                        binding.nextButton.text = "Далее"
                    }
                }
                if (tab?.position == 0) {
                    binding.backButton.visibility = View.INVISIBLE
                } else if (tab?.position == 2) {
                    binding.nextButton.text = "Закрыть"
                } else {
                    binding.backButton.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}