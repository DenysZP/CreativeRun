package com.dm.creativerun.domain.interactor.base

import kotlinx.coroutines.CancellationException

class UseCaseListener<T> {

    private var onLoading: ((Boolean) -> Unit)? = null
    private var onSuccess: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onCancel: ((CancellationException) -> Unit)? = null

    fun onLoading(function: (Boolean) -> Unit) {
        onLoading = function
    }

    fun onSuccess(function: (T) -> Unit) {
        onSuccess = function
    }

    fun onError(function: (Throwable) -> Unit) {
        onError = function
    }

    fun onCancel(function: (CancellationException) -> Unit) {
        onCancel = function
    }

    operator fun invoke(isLoading: Boolean) = onLoading?.invoke(isLoading)

    operator fun invoke(result: T) = onSuccess?.invoke(result)

    operator fun invoke(error: Throwable) = onError?.invoke(error)

    operator fun invoke(error: CancellationException) = onCancel?.invoke(error)
}