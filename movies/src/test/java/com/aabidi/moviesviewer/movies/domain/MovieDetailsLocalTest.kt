package com.aabidi.moviesviewer.movies.domain

import com.aabidi.moviesviewer.core.exceptions.NoDataException
import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.data.movies.local.dao.MovieDao
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsLocal
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests the interaction between Local layer and Room.
 *
 * Tests the results from Local functions via [Result]
 */
class MovieDetailsLocalTest {

    private val dao: MovieDao = mock()
    private val local = MovieDetailsLocal(dao)

    @Test
    fun whenGetMovieDetails_thenReturnSuccess() = runBlocking {
        mockMovieCached()

        val movieData = local.getMovieDetails("2")

        assert(movieData is DataResult.Success<LocalMovie>)

        if (movieData is DataResult.Success<LocalMovie>)
            assertEquals(TEST_MOVIE, movieData.data)
    }

    @Test
    fun whenGetMovieDetails_thenReturnFailure() = runBlocking {
        mockMovieUnavailable()

        val movieData = local.getMovieDetails("2")

        assert(movieData is DataResult.Failure<LocalMovie>)

        if (movieData is DataResult.Failure<LocalMovie>)
            assertEquals(TEST_EXCEPTION, movieData.ex)
    }

    @Test
    fun whenSaveMovie_thenCallDao() = runBlocking {

        local.saveMovieDetails(TEST_MOVIE)

        verify(dao).insert(TEST_MOVIE)
    }

    private fun mockMovieCached() {
        whenever(dao.getMovieDetails("2")) doReturn (TEST_MOVIE)
    }

    private fun mockMovieUnavailable() {
        whenever(dao.getMovieDetails("2")) doReturn (null)
    }

    companion object {
        private val TEST_MOVIE = LocalMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = Date(),
            rating = 0f
        )

        private val TEST_EXCEPTION = NoDataException()
    }
}
