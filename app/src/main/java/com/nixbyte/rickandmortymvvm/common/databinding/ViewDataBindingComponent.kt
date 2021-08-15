package com.nixbyte.rickandmortymvvm.common.databinding

import androidx.databinding.DataBindingComponent

class ViewDataBindingComponent : DataBindingComponent {
    override fun getShapeableImageViewModel(): ShapeableImageViewModel {
        return ShapeableImageViewModel()
    }
}