package com.example.afinal.home

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {
    companion object {
        fun newInstance() = HomePageFragment()
    }

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sumSlider.progress = 5
        binding.sumSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.sum.text =
                        Editable.Factory.getInstance()
                            .newEditable((progress * 100).toString() + " ₽")
                }
                binding.sumMessage.text = if (progress * 100 < 500) "Минимум 500 рублей" else null
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        binding.sum.doOnTextChanged { text, _, _, _ ->
            binding.sumSlider.progress = text.toString().dropLast(2).toInt() / 100
            binding.sumMessage.text =
                if (text.toString().dropLast(2).toInt() > 10000) "Максимум 10000" else null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}