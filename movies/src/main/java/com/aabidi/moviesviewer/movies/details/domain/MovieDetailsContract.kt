package com.aabidi.moviesviewer.movies.details.domain

import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import kotlinx.coroutines.flow.Flow

interface MovieDetailsContract {

    interface Repository {
        suspend fun getMovieDetails(movieId: String): Flow<StateResult<MovieDetailsUiModel>>
        suspend fun saveMovieRating(movieId: String, rating: Float)
    }

    interface Local {
        suspend fun saveMovieDetails(movie: LocalMovie)
        suspend fun saveMovieRating(movieId: String, rating: Float)
        suspend fun getMovieDetails(movieId: String): DataResult<LocalMovie>
    }

    interface Remote {
        suspend fun getMovieDetails(movieId: String): DataResult<RemoteMovie>
    }

}
