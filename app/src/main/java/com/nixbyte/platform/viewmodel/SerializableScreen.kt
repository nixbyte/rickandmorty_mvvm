package com.nixbyte.platform.viewmodel

import java.io.Serializable

interface SerializableScreen : Serializable {
    companion object {
        const val ARG_SCREEN = "ARG_SCREEN"
    }
}