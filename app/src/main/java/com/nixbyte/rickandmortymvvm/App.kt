package com.nixbyte.rickandmortymvvm

import android.app.Application
import android.util.Log
import com.nixbyte.platform.model.Repository
import com.nixbyte.platform.model.RxTaskFactory
import com.nixbyte.platform.view.BaseApplication
import com.nixbyte.rickandmortymvvm.model.NetworkLocationsRepository

class App : Application(), BaseApplication {
    val TAG = App::class.java.simpleName

    private val rxTasksFactory = RxTaskFactory()

    override val repositories: List<Repository> = listOf(NetworkLocationsRepository(rxTasksFactory))

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.e(TAG, "onTerminate: ")
    }
}