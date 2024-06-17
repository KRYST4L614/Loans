package com.example.afinal.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.afinal.MainActivity
import com.example.afinal.util.PasswordTransformation
import com.example.afinal.databinding.AuthBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout


class AuthBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AuthBottomSheet()
    }

    private var _binding: AuthBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordEditText.transformationMethod = PasswordTransformation()
        binding.tableLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == 0) {
                        binding.repeatPasswordInputLayout.isVisible = false
                        binding.button.text = "Войти"
                    } else {
                        binding.repeatPasswordInputLayout.isVisible = true
                        binding.button.text = "Зарегистрироваться"
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            }
        )

        binding.button.setOnClickListener {
            (requireActivity() as MainActivity).onOnboarding()
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}