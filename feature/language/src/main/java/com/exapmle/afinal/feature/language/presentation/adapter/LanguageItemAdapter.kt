package com.exapmle.afinal.feature.language.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.feature.language.databinding.LanguageItemBinding

class LanguageItemAdapter(
    private val data: List<String>,
    private val callback: (Boolean) -> Unit
) : RecyclerView.Adapter<LanguageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            LanguageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(data[position], callback)
    }
}