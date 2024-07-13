package com.example.afinal.shared.loans.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.LoanConditions

@Database(entities = [Loan::class, LoanConditions::class], version = 1)
abstract class LoansDatabase : RoomDatabase() {
    abstract fun dao(): LoanDao

    companion object {
        private var instance: LoansDatabase? = null

        fun getDatabase(context: Context): LoansDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoansDatabase::class.java,
                    "Loans.db"
                )
                    .build()
            }
            return instance as LoansDatabase
        }
    }
}