package com.example.afinal.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentLanguageBinding
import com.example.afinal.home.adapter.LanguageItemAdapter

class LanguageFragment : Fragment() {
    companion object {
        fun newInstance() = LanguageFragment()
    }

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

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
        binding.toolbar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).supportFragmentManager.popBackStack()
        }

        binding.listView.adapter =
            LanguageItemAdapter(resources.getStringArray(R.array.language_items).toList()) {}
        binding.listView.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically() = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}