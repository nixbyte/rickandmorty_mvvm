package com.nixbyte.platform.model

import io.reactivex.Single

typealias RxTaskFunction<T> = () -> Single<T>

/**
 * Factory for creating async task instances ([Task]) from synchronous code defined by [TaskFunction]
 */
interface RxTasksFactory : Repository {
    /**
     * Create a new [Task] instance from the specified body.
     */
    fun <T> async(body: RxTaskFunction<T>): Task<T>
}