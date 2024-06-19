package com.example.afinal.feature.home.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.feature.home.R
import com.example.afinal.feature.home.databinding.FragmentMenuPageBinding
import com.example.afinal.feature.home.di.DaggerHomeComponent
import com.example.afinal.feature.home.presentation.HomeViewModel
import com.example.afinal.feature.home.ui.adapter.MenuItemAdapter
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import javax.inject.Inject

class MenuPageFragment : Fragment() {
    companion object {
        fun newInstance() = MenuPageFragment()
    }

    private var _binding: FragmentMenuPageBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMenuPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listView.adapter =
            MenuItemAdapter(resources.getStringArray(R.array.menu_items).toList()) {
                when (it) {
                    "Помощь" -> viewModel.openSupport()
                    "Предложения для вас" -> viewModel.openSpecial()
                    "Язык" -> viewModel.openLanguage()
                    "Отделения банка" -> {}
                }
            }
        binding.listView.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically() = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}