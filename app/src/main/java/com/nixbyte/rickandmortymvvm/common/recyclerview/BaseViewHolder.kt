package com.nixbyte.rickandmortymvvm.common.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.nixbyte.rickandmortymvvm.BR

open class BaseViewHolder<T>(protected val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    open fun bind(model: T) {
        binding.setVariable(BR.model, model)
        binding.executePendingBindings()
    }
}