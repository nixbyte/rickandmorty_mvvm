package com.nixbyte.platform.navigation

import com.nixbyte.platform.utils.ResourceActions
import com.nixbyte.platform.viewmodel.SerializableScreen

class IntermediateNavigator : Navigation {

    private val targetNavigator = ResourceActions<Navigation>()

    override fun showScreen(screen: SerializableScreen) = targetNavigator {
        it.showScreen(screen)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    fun setTarget(navigator: Navigation?) {
        targetNavigator.resource = navigator
    }

    fun clear() {
        targetNavigator.clear()
    }
}