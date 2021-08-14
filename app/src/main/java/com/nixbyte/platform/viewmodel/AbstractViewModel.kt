package com.nixbyte.platform.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nixbyte.platform.model.PendingResult
import com.nixbyte.platform.model.Task
import com.nixbyte.platform.model.Result
import com.nixbyte.platform.model.TaskListener
import io.reactivex.disposables.CompositeDisposable

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>

/**
 * Base class for all view-models.
 */
open class AbstractViewModel : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()
    val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        tasks.forEach { it.cancel() }
        tasks.clear()
    }

    /**
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onViewModelResult(result: Any) {

    }

    /**
     * Launch task asynchronously, listen for its result and
     * automatically unsubscribe the listener in case of view-model destroying.
     */
    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null) {
        tasks.add(this)
        this.enqueue {
            tasks.remove(this)
            listener?.invoke(it)
        }
    }

    /**
     * Launch task asynchronously and map its result to the specified
     * [liveResult].
     * Task is cancelled automatically if view-model is going to be destroyed.
     */
    fun <T> Task<T>.into(liveResult: MutableLiveResult<T>) {
        liveResult.value = PendingResult()
        this.safeEnqueue {
            liveResult.value = it
        }
    }
}