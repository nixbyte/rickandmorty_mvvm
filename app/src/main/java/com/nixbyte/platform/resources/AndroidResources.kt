package com.nixbyte.platform.resources

import android.content.Context

class AndroidResources(
    private val appContext: Context
) : Resources {

    override fun getString(messageRes: Int, vararg args: Any): String {
        return appContext.getString(messageRes, *args)
    }
}