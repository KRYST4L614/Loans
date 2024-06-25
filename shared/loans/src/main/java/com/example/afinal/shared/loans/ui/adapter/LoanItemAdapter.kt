package com.example.afinal.shared.loans.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.shared.loans.databinding.LoanItemBinding
import com.example.afinal.shared.loans.domain.entities.Loan

class LoanItemAdapter(
    private var data: List<Loan>,
    private val onClick: (loan: Loan) -> Unit
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
        data[position].let { loan ->
            holder.bind(loan)
            holder.itemView.setOnClickListener {
                onClick(loan)
            }
        }
    }
}