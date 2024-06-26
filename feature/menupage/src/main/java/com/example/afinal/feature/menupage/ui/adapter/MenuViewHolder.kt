package com.example.afinal.feature.menupage.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.feature.menupage.databinding.MenuItemBinding

class MenuViewHolder(
    private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(name: String) {
        binding.name.text = name
    }
}