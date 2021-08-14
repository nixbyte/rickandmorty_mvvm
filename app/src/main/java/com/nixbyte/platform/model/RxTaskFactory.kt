package com.nixbyte.platform.model

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxTaskFactory : RxTasksFactory {

    override fun <T> async(body: RxTaskFunction<T>): Task<T> {
        return RxTask(body)
    }

    class RxTask<T>(private val body: RxTaskFunction<T>) : Task<T> {
        var subscription: Disposable? = null
        var cancelled = false

        val TAG = RxTask::class.simpleName

        override fun await(): T = body().blockingGet()

        override fun enqueue(listener: TaskListener<T>) {
            val data = body()
            subscription = data
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                publishResult(listener, SuccessResult(result))
            } , {
                    Log.e(TAG, "enqueue: ${it.message}", )
                publishResult(listener,ErrorResult(Exception(it.message)))
            })
        }

        override fun cancel() {
            cancelled = true
            subscription?.apply {
                if (!isDisposed) dispose()
            }
        }

        private fun publishResult(listener: TaskListener<T>, result: FinalResult<T>) {
            if (cancelled) return
            listener(result)
        }
    }
}