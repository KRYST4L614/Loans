package com.example.afinal.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bs = AuthBottomSheet().also {
            it.isCancelable = false
        }
        bs.show(childFragmentManager, "")
        binding.progress.isVisible = false
        binding.logo.updatePadding(
            bottom = (500 * requireContext().resources.displayMetrics.density).toInt(),
            top = (136 * requireContext().resources.displayMetrics.density).toInt()
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}