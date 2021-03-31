package com.aabidi.moviesviewer.movies.domain

import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.exceptions.NoDataException
import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsContract
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsRepo
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests the interaction between Repo, Local & Remote.
 *
 * Tests the results from Repo via [StateResult]
 */
class MovieDetailsRepoTest {

    private val local: MovieDetailsContract.Local = mock()
    private val remote: MovieDetailsContract.Remote = mock()

    private val repo = MovieDetailsRepo(local, remote)

    @Test
    fun whenGetMovieDetails_andCached_thenReturnCached() = runBlocking {
        mockMovieCachedLocally()

        repo.getMovieDetails("2").collect {

            assert(it is StateResult.Success<MovieDetailsUiModel>)

            if (it is StateResult.Success<MovieDetailsUiModel>)
                assertEquals(MovieDetailsUiModel(TEST_LOCAL_MOVIE), it.data)
        }
    }

    @Test
    fun whenGetMovieDetails_andCached_thenRemoteIsNotCalled() {
        runBlocking {
            mockMovieCachedLocally()

            repo.getMovieDetails("2")

            verify(remote, never()).getMovieDetails("2")
        }
    }

    @Test
    fun whenGetMovieDetails_andNotCached_thenReturnRemote() = runBlocking {
        mockMovieNotCachedLocally()
        mockRemoteMovieSucces()

        repo.getMovieDetails("2").collect {

            verify(remote).getMovieDetails("2")

            assert(it is StateResult.Success<MovieDetailsUiModel>)

            if (it is StateResult.Success<MovieDetailsUiModel>)
                assertEquals(MovieDetailsUiModel(TEST_REMOTE_MOVIE), it.data)
        }
    }

    @Test
    fun whenGetMovieDetails_andNotCached_thenCachedLocally() = runBlocking {
        mockMovieNotCachedLocally()
        mockRemoteMovieSucces()

        repo.getMovieDetails("2").collect {

            verify(remote).getMovieDetails("2")
            verify(local).saveMovieDetails(TEST_LOCAL_MOVIE)
        }
    }

    @Test
    fun whenGetMovieDetails_andNotCached_andRemoteFails_thenReturnFailure() = runBlocking {
        mockMovieNotCachedLocally()
        mockRemoteMovieFailure()

        repo.getMovieDetails("2").collect {

            assert(it is StateResult.Failure)

            if (it is StateResult.Failure)
                assertEquals(TEST_EXCEPTION, it.ex)
        }
    }

    private fun mockMovieCachedLocally() = runBlocking {
        whenever(local.getMovieDetails("2")) doReturn (DataResult.Success(TEST_LOCAL_MOVIE))
    }

    private fun mockMovieNotCachedLocally() = runBlocking {
        whenever(local.getMovieDetails("2")) doReturn (DataResult.Failure(TEST_EXCEPTION))
    }

    private fun mockRemoteMovieSucces() = runBlocking {
        whenever(remote.getMovieDetails("2")) doReturn (DataResult.Success(TEST_REMOTE_MOVIE))
    }

    private fun mockRemoteMovieFailure() = runBlocking {
        whenever(remote.getMovieDetails("2")) doReturn (DataResult.Failure(TEST_EXCEPTION))
    }

    companion object {

        private val TEST_DATE = Date()

        private val TEST_REMOTE_MOVIE = RemoteMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = TEST_DATE
        )

        private val TEST_LOCAL_MOVIE = LocalMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = TEST_DATE,
            rating = 0f
        )

        private val TEST_EXCEPTION = NoDataException()
    }
}
