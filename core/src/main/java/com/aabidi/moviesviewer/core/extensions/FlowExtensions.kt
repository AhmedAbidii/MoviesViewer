package com.aabidi.moviesviewer.core.extensions

import androidx.lifecycle.MutableLiveData
import com.aabidi.moviesviewer.core.dispatchers.Dispatcher
import com.aabidi.moviesviewer.core.domain.StateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

suspend fun <T> Flow<T>.collectOn(liveData: MutableLiveData<T>, postValue: Boolean = false) {
    if (postValue) collect { liveData.postValue(it) }
    else collect { liveData.value = it }
}

fun <T> Flow<T>.flowOnBack(dispatcher: Dispatcher) = flowOn(dispatcher.io)

suspend fun <T> FlowCollector<StateResult<T>>.emitSuccess(data: T) = emit(StateResult.Success(data))

suspend fun <T> FlowCollector<StateResult<T>>.emitFailure(throwable: Throwable) =
    emit(StateResult.Failure(throwable))
