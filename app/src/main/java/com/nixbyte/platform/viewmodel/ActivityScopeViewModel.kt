package com.nixbyte.platform.viewmodel

import androidx.lifecycle.ViewModel
import com.nixbyte.platform.navigation.IntermediateNavigator
import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.resources.Resources

class ActivityScopeViewModel(
    val resources: Resources,
    val navigator: IntermediateNavigator
) : ViewModel(),
    Navigation by navigator,
    Resources by resources {

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }
}