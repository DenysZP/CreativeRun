package com.dm.creativerun.domain.interactor.base

import kotlinx.coroutines.*
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, Params : Any> {

    private lateinit var parentJob: Job
    private lateinit var params: Params
    private var isAttachedToLifecycle = false
    private val backgroundContext: CoroutineContext = Dispatchers.IO
    private val foregroundContext: CoroutineContext = Dispatchers.Main

    fun withParams(params: Params) = this.also { it.params = params }

    open fun execute(useCaseListener: UseCaseListener<T>.() -> Unit) {
        checkIsAttachedToLifecycle()
        val listener = UseCaseListener<T>().apply { useCaseListener() }
        cancel()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            listener(true)
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
                }
                listener(result)
            } catch (cancellationException: CancellationException) {
                listener(cancellationException)
            } catch (e: Exception) {
                e.printStackTrace()
                listener(e)
            } finally {
                listener(false)
            }
        }
    }

    fun attachToLifecycle() {
        isAttachedToLifecycle = true
    }

    fun cancel() {
        if (this::parentJob.isInitialized) {
            parentJob.apply {
                cancelChildren()
                cancel()
            }
        }
    }

    protected abstract suspend fun executeOnBackground(): T

    protected fun getParams(): Params {
        if (this::params.isInitialized) {
            return params
        } else {
            throw RuntimeException(
                "You have to initialize the required parameters " +
                        "of ${this.javaClass.name} before execute."
            )
        }
    }

    protected suspend fun <X> runAsync(
        context: CoroutineContext = backgroundContext,
        block: suspend () -> X
    ): Deferred<X> {
        return CoroutineScope(context + parentJob).async {
            block.invoke()
        }
    }

    private fun checkIsAttachedToLifecycle() {
        if (!isAttachedToLifecycle) {
            throw RuntimeException("You have to attach ${this.javaClass.name} to the lifecycle.")
        }
    }
}