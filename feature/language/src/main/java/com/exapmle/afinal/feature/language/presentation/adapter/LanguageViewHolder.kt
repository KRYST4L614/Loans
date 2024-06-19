package com.exapmle.afinal.feature.language.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.feature.language.databinding.LanguageItemBinding

class LanguageViewHolder(
    private val binding: LanguageItemBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(name: String, callback: (Boolean) -> Unit) {
        binding.name.text = name
    }
}