package com.aabidi.moviesviewer.core.domain

sealed class StateResult<out T> {

    /** Denotes that we are loading something **/
    data class Loading<T>(val isLoading: Boolean = true) : StateResult<T>()

    /** Denotes that we have data **/
    data class Success<T>(val data: T) : StateResult<T>()

    /** Denotes a failure to fetch the data **/
    data class Failure<T>(val ex: Throwable) : StateResult<T>()

}
