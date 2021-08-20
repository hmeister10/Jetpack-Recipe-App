package com.example.samplerecipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    val isDark = mutableStateOf(false)

    fun toggleDarkTheme() {
        isDark.value = !isDark.value
    }
}