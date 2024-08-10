package com.example.afinal.shared.resourceprovider

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Context
) {
    fun getString(resId: Int): String = context.getString(resId)
    fun getColor(resId: Int): Int = context.getColor(resId)
}