package com.nixbyte.rickandmortymvvm.common.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.nixbyte.rickandmortymvvm.BR
import com.nixbyte.rickandmortymvvm.R
import com.squareup.picasso.Picasso

class ShapeableImageViewModel(var _url: String = "") : BaseObservable() {

    @get:Bindable
    var url get() = _url; set(value) {
        _url = value
        notifyPropertyChanged(BR.url)
    }

    @BindingAdapter("url")
    fun url(imageView: ShapeableImageView, url: String) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(imageView)
    }
}