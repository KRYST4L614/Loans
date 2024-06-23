package com.example.afinal.util

import android.text.Editable

fun CharSequence.toEditable(): Editable {
    return Editable.Factory.getInstance().newEditable(this)
}