package com.aabidi.moviesviewer.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.exceptions.NoDataException
import com.aabidi.moviesviewer.core.extensions.emitFailure
import com.aabidi.moviesviewer.core.extensions.emitSuccess
import com.aabidi.moviesviewer.core.testing.TestingDispatcher
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsContract
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import com.aabidi.moviesviewer.movies.details.viewmodel.MovieDetailsViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Tests the interaction between ViewModel and Repo.
 *
 * Tests the LiveData emissions to the UI layer.
 */
class MovieDetailsViewModelTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val observer: Observer<StateResult<MovieDetailsUiModel>> = mock()
    private val repo: MovieDetailsContract.Repository = mock()

    private val viewModel = MovieDetailsViewModel(repo, TestingDispatcher())

    @Test
    fun whenGetMovieDetails_thenLoadingIsEmitted() {
        mockRepoSuccess()

        viewModel.movieDetails.observeForever(observer)

        viewModel.getMovieDetails("2")

        verify(observer).onChanged(StateResult.Loading())
    }

    @Test
    fun whenGetMovieDetails_thenUiModelIsEmitted() {
        mockRepoSuccess()

        viewModel.movieDetails.observeForever(observer)

        viewModel.getMovieDetails("2")

        assert(viewModel.movieDetails.value is StateResult.Success)

        assertEquals(StateResult.Success(TEST_UI_MODEL), viewModel.movieDetails.value)
    }

    @Test
    fun whenGetMovieDetails_andHasData_thenLoadingIsNotEmitted() {
        runBlockingTest {
            mockRepoSuccess()

            viewModel.movieDetails.observeForever(observer)

            viewModel.getMovieDetails("2")

            assert(viewModel.movieDetails.value is StateResult.Success)

            viewModel.getMovieDetails("2")

            verify(observer, times(1)).onChanged(StateResult.Loading())

            verify(repo, times(1)).getMovieDetails("2")
        }
    }

    @Test
    fun whenGetMovieDetails_andNoCacheOrNetwork_thenFailureIsEmitted() {
        mockRepoFailure()

        viewModel.movieDetails.observeForever(observer)

        viewModel.getMovieDetails("2")

        assert(viewModel.movieDetails.value is StateResult.Failure)

        assertEquals(StateResult.Failure<MovieUiModel>(TEST_EXCEPTION), viewModel.movieDetails.value)
    }

    private fun mockRepoSuccess() = runBlockingTest {
        whenever(repo.getMovieDetails("2")) doReturn flow { emitSuccess(TEST_UI_MODEL) }
    }

    private fun mockRepoFailure() = runBlockingTest {
        whenever(repo.getMovieDetails("2")) doReturn flow { emitFailure<MovieDetailsUiModel>(TEST_EXCEPTION) }
    }

    companion object {

        private val TEST_UI_MODEL = MovieDetailsUiModel(
            title = "Test Movie",
            description = "An overview for a Test Movie",
            backdropImage = "https://picsum.photos/200/300",
            posterImage = "https://picsum.photos/200/300",
            releaseYear = "2019",
            rating = 0f
        )

        private val TEST_EXCEPTION = NoDataException()
    }
}
