package com.example.afinal.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.databinding.MenuItemBinding
import com.example.afinal.menu.adapter.MenuViewHolder

class MenuItemAdapter(
    private val data: List<String>,
    private val callback: (String) -> Unit
) : RecyclerView.Adapter<MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            callback(data[position])
        }
    }
}