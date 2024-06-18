package com.example.afinal.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentHomeBinding
import com.example.afinal.menu.MenuPageFragment
import com.example.afinal.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        binding.toolbar.menu.findItem(R.id.info).setOnMenuItemClickListener {
            (requireActivity() as MainActivity).onOnboarding()
            true
        }

        binding.viewPager.adapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            listOf(
                HomePageFragment::newInstance,
                MenuPageFragment::newInstance
            )
        )

        binding.tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.toolbar.title = if (position == 0) "Главная" else "Меню"
                    binding.tableLayout.getTabAt(position)?.select()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}