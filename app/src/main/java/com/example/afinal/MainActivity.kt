package com.example.afinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.auth.AuthFragment
import com.example.afinal.databinding.ActivityMainBinding
import com.example.afinal.onboarding.HomeFragment
import com.example.afinal.onboarding.OnboardingFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, AuthFragment())
                .addToBackStack("")
                .commit()
        }
    }

    fun onOnboarding() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, OnboardingFragment.newInstance()).addToBackStack(null)
            .commit()
    }

    fun onHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment.newInstance()).addToBackStack(null)
            .commit()
    }
}