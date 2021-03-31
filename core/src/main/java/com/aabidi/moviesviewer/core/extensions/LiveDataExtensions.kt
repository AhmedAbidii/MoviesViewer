package com.aabidi.moviesviewer.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aabidi.moviesviewer.core.domain.StateResult

/**
 * Check if the LiveData does not hold a value.
 */
fun LiveData<*>.isEmpty() = value == null

/**
 * Check if the LiveData holds a value.
 */
fun LiveData<*>.hasValue() = !isEmpty()

/**
 * Checks if the Result LiveData is not idle
 * i.e. it is loading something, or contains a result
 */
fun <T> LiveData<StateResult<T>>.isNotIdle() =
    hasValue() && (value is StateResult.Loading || value is StateResult.Success)

fun <T> LiveData<StateResult<T>>.isLoading() = hasValue() && value is StateResult.Loading

/**
 * Set a Loading State on this LiveData.
 *
 * @param isLoading The Result Loading boolean
 * @param postValue Whether the value should be set (same thread) or posted
 */
fun <T> MutableLiveData<StateResult<T>>.setLoading(
    isLoading: Boolean = true,
    postValue: Boolean = false
) {
    if (postValue) postValue(StateResult.Loading(isLoading))
    else value = StateResult.Loading(isLoading)
}

/**
 * Wrapper over the LiveData observe for using Lambdas.
 */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline codeBlock: (data: T) -> Unit

) = liveData.observe(this, { codeBlock(it) })
