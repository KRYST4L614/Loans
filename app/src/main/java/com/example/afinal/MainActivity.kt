package com.example.afinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.address.AddressesFragment
import com.example.afinal.auth.AuthFragment
import com.example.afinal.databinding.ActivityMainBinding
import com.example.afinal.home.HomeFragment
import com.example.afinal.menu.LanguageFragment
import com.example.afinal.menu.SpecialFragment
import com.example.afinal.menu.SupportFragment
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

    fun onSupport() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, SupportFragment.newInstance()).addToBackStack(null)
            .commit()
    }

    fun onSpecial() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, SpecialFragment.newInstance()).addToBackStack(null)
            .commit()
    }

    fun onLanguage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, LanguageFragment.newInstance()).addToBackStack(null)
            .commit()
    }

    fun onAddresses() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, AddressesFragment.newInstance()).addToBackStack(null)
            .commit()
    }
}