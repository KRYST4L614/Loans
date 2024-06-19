package com.exapmle.afinal.feature.language.presentation

import androidx.lifecycle.ViewModel
import com.exapmle.afinal.feature.language.LanguageRouter
import javax.inject.Inject

class LanguageViewModel @Inject constructor(
    private val router: LanguageRouter
) : ViewModel() {
    fun close() = router.close()
}