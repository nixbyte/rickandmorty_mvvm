package com.nixbyte.platform.resources

interface Resources {
    fun getString(messageRes: Int, vararg args: Any): String
}