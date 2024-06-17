package com.example.afinal.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.databinding.MenuItemBinding

class MenuViewHolder(
    private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(name: String) {
        binding.name.text = name
    }
}