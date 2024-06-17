package com.example.afinal.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentOnboardingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class OnboardingFragment : Fragment() {
    companion object {
        fun newInstance() = OnboardingFragment()
    }

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

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
        binding.toolbar.inflateMenu(R.menu.onboarding_menu)
        binding.toolbar.title = ""
        setMenuVisibility(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).onHome()
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
        TabLayoutMediator(binding.tableLayout, binding.viewPager) { tab, position ->
        }.attach()

        with(binding) {
            backButton.setOnClickListener {
                viewPager.currentItem = (viewPager.currentItem - 1).coerceAtLeast(0)
            }

            nextButton.setOnClickListener {
                if (viewPager.currentItem == 2) {
                    (requireActivity() as MainActivity).onHome()
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