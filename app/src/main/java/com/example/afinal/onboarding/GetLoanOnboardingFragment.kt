package com.example.afinal.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.GetLoanOnboardingBinding

class GetLoanOnboardingFragment : Fragment() {
    companion object {
        fun newInstance() = GetLoanOnboardingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = GetLoanOnboardingBinding.inflate(layoutInflater).root
}