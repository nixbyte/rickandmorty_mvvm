package com.nixbyte.rickandmortymvvm.common.recyclerview

import android.view.View

interface ItemClickable {
    var onItemClick: (View) -> Unit
}