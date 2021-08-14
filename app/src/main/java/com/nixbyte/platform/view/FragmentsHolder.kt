package com.nixbyte.platform.view

import com.nixbyte.platform.viewmodel.ActivityScopeViewModel

interface FragmentsHolder {
    fun notifyScreenUpdates()
    fun getActivityScopeViewModel(): ActivityScopeViewModel
}