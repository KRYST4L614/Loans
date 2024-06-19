package com.example.afinal.feature.onboarding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.feature.onboarding.databinding.ArrangeLoanOnboardingBinding

class ArrangeLoanOnboardingFragment : Fragment() {

    companion object {
        fun newInstance() = ArrangeLoanOnboardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ArrangeLoanOnboardingBinding.inflate(layoutInflater).root
}