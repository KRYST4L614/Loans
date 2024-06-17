package com.example.afinal.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentMenuPageBinding
import com.example.afinal.home.adapter.MenuItemAdapter

class MenuPageFragment : Fragment() {
    companion object {
        fun newInstance() = MenuPageFragment()
    }

    private var _binding: FragmentMenuPageBinding? = null
    private val binding get() = _binding!!

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
                    "Помощь" -> (requireActivity() as MainActivity).onSupport()
                    "Предложения для вас" -> (requireActivity() as MainActivity).onSpecial()
                    "Язык" -> (requireActivity() as MainActivity).onLanguage()
                    "Отделения банка" -> (requireActivity() as MainActivity).onAddresses()
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