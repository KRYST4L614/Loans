package com.example.afinal.feature.menupage.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.feature.menupage.R
import com.example.afinal.feature.menupage.databinding.FragmentMenuPageBinding
import com.example.afinal.feature.menupage.di.DaggerMenuPageComponent
import com.example.afinal.feature.menupage.presentation.MenuPageViewModel
import com.example.afinal.feature.menupage.presentation.adapter.MenuItemAdapter
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
    private val viewModel by viewModels<MenuPageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerMenuPageComponent
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
                    "Мои займы" -> viewModel.openMyLoansPage()
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

    private fun setupMenu() {
        binding.toolbar.menu.findItem(com.example.afinal.component.resources.R.id.info).setOnMenuItemClickListener {
            viewModel.openOnboarding()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}