package com.example.afinal.feature.auth.util

import android.text.method.PasswordTransformationMethod
import android.view.View

class PasswordTransformation : PasswordTransformationMethod() {

    companion object {
        const val HIDE_CHAR = '*'
    }

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(source)
    }

    inner class PasswordCharSequence (private val source: CharSequence) : CharSequence {

        override val length: Int
            get() = source.length

        override fun get(index: Int): Char = HIDE_CHAR

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source.subSequence(startIndex, endIndex)
        }
    }
}