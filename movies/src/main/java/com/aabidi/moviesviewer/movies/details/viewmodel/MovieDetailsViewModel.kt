package com.aabidi.moviesviewer.movies.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aabidi.moviesviewer.core.dispatchers.Dispatcher
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.collectOn
import com.aabidi.moviesviewer.core.extensions.flowOnBack
import com.aabidi.moviesviewer.core.extensions.isNotIdle
import com.aabidi.moviesviewer.core.extensions.setLoading
import com.aabidi.moviesviewer.core.viewmodel.BaseViewModel
import com.aabidi.moviesviewer.movies.details.di.MovieDetailsModule
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsContract
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel


class MovieDetailsViewModel(
    private val movieRepo: MovieDetailsContract.Repository,
    private val dispatcher: Dispatcher

) : BaseViewModel(dispatcher) {

    private val _movieDetails = MutableLiveData<StateResult<MovieDetailsUiModel>>()

    /** Exposed LiveData **/
    val movieDetails: LiveData<StateResult<MovieDetailsUiModel>> = _movieDetails

    fun getMovieDetails(movieId: String) {
        if (movieDetails.isNotIdle()) return

        _movieDetails.setLoading()
        launchOnMain {
            movieRepo.getMovieDetails(movieId = movieId)
                .flowOnBack(dispatcher)
                .collectOn(_movieDetails)
        }
    }

    fun saveMovieRating(movieId: String, rating: Float) {
        launchOnDb {
            movieRepo.saveMovieRating(movieId, rating)
        }
    }

    override fun onCleared() {
        MovieDetailsModule.unload()
        super.onCleared()
    }
}
