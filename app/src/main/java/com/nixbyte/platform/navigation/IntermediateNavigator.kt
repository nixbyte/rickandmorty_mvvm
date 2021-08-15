package com.nixbyte.platform.navigation

import com.nixbyte.platform.utils.ResourceActions
import com.nixbyte.platform.viewmodel.SerializableScreen

class IntermediateNavigator : Navigation {

    private val targetNavigator = ResourceActions<Navigation>()

    override fun showScreenAndClearStack(screen: SerializableScreen) = targetNavigator {
        it.showScreenAndClearStack(screen)
    }

    override fun addScreen(screen: SerializableScreen) = targetNavigator {
        it.addScreen(screen)
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