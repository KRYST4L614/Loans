package com.example.afinal.feature.home.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.feature.home.LocalCiceroneHolder
import com.example.afinal.feature.home.R
import com.example.afinal.feature.home.databinding.FragmentHomeBinding
import com.example.afinal.feature.home.di.DaggerHomeComponent
import com.example.afinal.feature.home.presentation.HomeViewModel
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigatorHolder: LocalCiceroneHolder
    private val navigator by lazy {
        AppNavigator(requireActivity(), R.id.frameLayout, childFragmentManager)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerHomeComponent
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
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupTableLayout()
//        setupViewPager()
    }

    private fun setupMenu() {
        binding.toolbar.menu.findItem(R.id.info).setOnMenuItemClickListener {
            viewModel.openOnboarding()
            true
        }
    }

//    private fun setupViewPager() {
//        with(binding) {
//            viewPager.adapter = ViewPagerAdapter(
//                childFragmentManager,
//                lifecycle,
//                viewModel.viewPagerFragments
//            )
//
//            viewPager.isUserInputEnabled = false
//
//            viewPager.registerOnPageChangeCallback(
//                object : ViewPager2.OnPageChangeCallback() {
//                    override fun onPageSelected(position: Int) {
//                        super.onPageSelected(position)
//                        binding.toolbar.title =
//                            getString(if (position == 0) R.string.home else R.string.menu)
//                        binding.tableLayout.getTabAt(position)?.select()
//                    }
//                }
//            )
//        }
//    }

    private fun setupTableLayout() {
        with(binding) {
            tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel.handleTabChange(tab.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.getCicerone().getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.getCicerone().getNavigatorHolder().removeNavigator()
        _binding = null
    }
}