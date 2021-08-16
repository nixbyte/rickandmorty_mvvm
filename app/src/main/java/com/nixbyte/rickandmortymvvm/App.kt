package com.nixbyte.rickandmortymvvm

import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.nixbyte.rickandmortymvvm.common.databinding.ViewDataBindingComponent
import com.nixbyte.rickandmortymvvm.model.api.API
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val picassoBuilder = Picasso.Builder(applicationContext)
            .downloader(OkHttp3Downloader(API.A.okhttp()))
            .loggingEnabled(true)
        Picasso.setSingletonInstance(picassoBuilder.build())
        DataBindingUtil.setDefaultComponent(ViewDataBindingComponent())
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

