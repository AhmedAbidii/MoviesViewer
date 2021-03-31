package com.aabidi.moviesviewer.core.testing

import com.aabidi.moviesviewer.core.dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined

/**
 * Dispatcher for Unit testing
 */
class TestingDispatcher : Dispatcher {

    override val main: CoroutineDispatcher = Unconfined

    override val io: CoroutineDispatcher = Unconfined

    override val db: CoroutineDispatcher = Unconfined

    override val computation: CoroutineDispatcher = Unconfined

}
