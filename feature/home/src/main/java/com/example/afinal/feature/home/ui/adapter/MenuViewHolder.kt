package com.example.afinal.feature.home.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.feature.home.databinding.MenuItemBinding

class MenuViewHolder(
    private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(name: String) {
        binding.name.text = name
    }
}