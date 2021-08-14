package com.nixbyte.platform.navigation

import androidx.annotation.StringRes
import com.nixbyte.platform.viewmodel.SerializableScreen

interface ScreenNavigation {
    fun showScreen(screen: SerializableScreen)
    fun addScreen(screen: SerializableScreen)
}