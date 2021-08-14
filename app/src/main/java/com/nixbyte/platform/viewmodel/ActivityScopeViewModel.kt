package com.nixbyte.platform.viewmodel

import androidx.lifecycle.ViewModel
import com.nixbyte.platform.navigation.IntermediateNavigator
import com.nixbyte.platform.navigation.Navigation
import com.nixbyte.platform.uiactions.UiActions

class ActivityScopeViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
) : ViewModel(),
    Navigation by navigator,
    UiActions by uiActions {

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }
}