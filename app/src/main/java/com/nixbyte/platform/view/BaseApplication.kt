package com.nixbyte.platform.view

import com.nixbyte.platform.model.Repository

interface BaseApplication {
    val repositories: List<Repository>
}