package com.exapmle.afinal.feature.language.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.feature.language.R
import com.example.afinal.feature.language.databinding.FragmentLanguageBinding
import com.example.afinal.shared.fragmentDependencies.FragmentDependenciesStore
import com.exapmle.afinal.feature.language.di.DaggerLanguageComponent
import com.exapmle.afinal.feature.language.presentation.LanguageViewModel
import com.exapmle.afinal.feature.language.ui.adapter.LanguageItemAdapter
import javax.inject.Inject

class LanguageFragment : Fragment() {
    companion object {
        fun newInstance() = LanguageFragment()
    }

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LanguageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerLanguageComponent
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
        _binding = FragmentLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupClickListeners()
    }

    private fun setupMenu() {
        with(binding) {
            recyclerView.adapter =
                LanguageItemAdapter(resources.getStringArray(R.array.language_items).toList())
            recyclerView.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically() = false
            }
        }
    }

    private fun setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.close()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.close()
            this.remove()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}