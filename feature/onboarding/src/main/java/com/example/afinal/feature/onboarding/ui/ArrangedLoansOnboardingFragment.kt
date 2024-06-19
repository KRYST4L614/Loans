package com.example.afinal.feature.onboarding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.feature.onboarding.databinding.ArrangedLoansOnboardingBinding

class ArrangedLoansOnboardingFragment : Fragment() {
    companion object {
        fun newInstance() = ArrangedLoansOnboardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ArrangedLoansOnboardingBinding.inflate(layoutInflater).root
}