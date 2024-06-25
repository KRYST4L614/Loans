package com.example.afinal.feature.homepage.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.feature.homepage.databinding.LoanItemBinding
import com.example.afinal.feature.homepage.domain.entities.Loan

class LoanItemAdapter(
    private var data: List<Loan>
) : RecyclerView.Adapter<LoanViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Loan>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        return LoanViewHolder(
            LoanItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        data[position].let {
            holder.bind(it)
        }
    }
}