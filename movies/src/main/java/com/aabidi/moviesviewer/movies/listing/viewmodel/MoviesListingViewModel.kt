package com.aabidi.moviesviewer.movies.listing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aabidi.moviesviewer.core.dispatchers.Dispatcher
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.collectOn
import com.aabidi.moviesviewer.core.extensions.flowOnBack
import com.aabidi.moviesviewer.core.extensions.hasValue
import com.aabidi.moviesviewer.core.extensions.setLoading
import com.aabidi.moviesviewer.core.pagination.Pagination
import com.aabidi.moviesviewer.core.viewmodel.BaseViewModel
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import com.aabidi.moviesviewer.movies.listing.di.SectionMoviesModule
import com.aabidi.moviesviewer.movies.listing.domain.MoviesListingContract

class MoviesListingViewModel(
    private val sectionRepo: MoviesListingContract.Repository,
    private val dispatcher: Dispatcher

) : BaseViewModel(dispatcher) {

    private val _discoverMovies = MutableLiveData<StateResult<Pagination.Result<MovieUiModel>>>()

    /** Exposed LiveData **/
    val discoverMovies: LiveData<StateResult<Pagination.Result<MovieUiModel>>> = _discoverMovies

    fun getMovies() {
        if (_discoverMovies.hasValue()) return
        getMoreMovies()
    }

    fun getMoreMovies() {
        _discoverMovies.setLoading(postValue = true)
        launchOnMain {
            sectionRepo.getMovies()
                .flowOnBack(dispatcher)
                .collectOn(_discoverMovies)
        }
    }

    override fun onCleared() {
        SectionMoviesModule.unload()
        super.onCleared()
    }
}
