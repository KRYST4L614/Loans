package com.example.afinal.shared.loans.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.afinal.shared.loans.R
import com.example.afinal.shared.loans.databinding.LoanItemBinding
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.Status.APPROVED
import com.example.afinal.shared.loans.domain.entities.Status.GRANTED
import com.example.afinal.shared.loans.domain.entities.Status.REGISTERED
import com.example.afinal.util.convertToString
import com.example.afinal.util.toSum
import com.example.afinal.component.resources.R as ComponentR


class LoanViewHolder(
    private val binding: LoanItemBinding
) : ViewHolder(
    binding.root
) {
    fun bind(loan: Loan) {
        with(binding) {
            sum.text =
                root.context.getString(ComponentR.string.sum).format(loan.amount.toSum())
            loanId.text = root.context.getString(R.string.loan_id).format(loan.id)
            date.text = loan.date.convertToString(
                "d MMMM, EEE",
                root.resources.configuration.locales[0]
            )
            status.text = when (loan.status) {
                APPROVED -> {
                    status.setTextColor(itemView.context.getColor(R.color.indicator_positive))
                    root.context.getString(R.string.loan_approved)
                }

                REGISTERED -> {
                    status.setTextColor(root.context.getColor(R.color.indicator_attention))
                    root.context.getString(R.string.loan_registered)
                }

                GRANTED -> {
                    status.setTextColor(root.context.getColor(ComponentR.color.fontSecondaryDay))
                    root.context.getString(R.string.loan_rejected)
                }

                else -> {
                    throw IllegalStateException("Illegal state of loan")
                }
            }
        }
    }
}