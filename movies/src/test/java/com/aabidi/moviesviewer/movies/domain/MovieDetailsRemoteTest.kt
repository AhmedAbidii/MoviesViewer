package com.aabidi.moviesviewer.movies.domain

import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.data.movies.remote.MovieWebService
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsRemote
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.util.*

/**
 * Tests the interaction between Remote & WebService layer.
 *
 * Tests the results from Remote functions via [DataResult]
 */
class MovieDetailsRemoteTest {

    private val webService: MovieWebService = mock()
    private val remote = MovieDetailsRemote(webService)

    @Test
    fun whenGetMovie_thenReturnSuccess() = runBlocking {
        mockApiSuccess()

        val apiResult = remote.getMovieDetails("2")

        assert(apiResult is DataResult.Success<RemoteMovie>)

        if (apiResult is DataResult.Success<RemoteMovie>)
            assertEquals(TEST_MOVIE, apiResult.data)
    }

    @Test
    fun whenGetMovie_thenReturnFailure() = runBlocking {
        mockApiFailure()

        val apiResult = remote.getMovieDetails("2")

        assert(apiResult is DataResult.Failure)

        if (apiResult is DataResult.Failure)
            assertEquals(NETWORK_EXCEPTION, apiResult.ex)
    }

    private suspend fun mockApiSuccess() {
        whenever(webService.getMovieAsync("2")) doReturn TEST_MOVIE
    }

    private suspend fun mockApiFailure() {
        whenever(webService.getMovieAsync("2")) doAnswer { throw NETWORK_EXCEPTION }
    }

    companion object {

        private val TEST_MOVIE = RemoteMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = Date()
        )

        private val NETWORK_EXCEPTION by lazy { IOException("Bad Network") }
    }
}
