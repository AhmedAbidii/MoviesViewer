package com.aabidi.moviesviewer.movies.listing.domain

import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.core.pagination.Pagination
import com.aabidi.moviesviewer.data.movies.remote.api.MovieListingEnvelope
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import kotlinx.coroutines.flow.Flow

interface MoviesListingContract {

    interface Repository {
        suspend fun getMovies(): Flow<StateResult<Pagination.Result<MovieUiModel>>>
    }

    interface Remote {
        suspend fun getPopularMovies(requestPage: Int = 1): DataResult<MovieListingEnvelope>
    }
}
