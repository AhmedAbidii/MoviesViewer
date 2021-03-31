package com.aabidi.moviesviewer.movies.details.domain

import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.emitFailure
import com.aabidi.moviesviewer.core.extensions.emitSuccess
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import kotlinx.coroutines.flow.flow

class MovieDetailsRepo(
    private val local: MovieDetailsContract.Local,
    private val remote: MovieDetailsContract.Remote
) : MovieDetailsContract.Repository {

    override suspend fun getMovieDetails(movieId: String) = flow<StateResult<MovieDetailsUiModel>> {
        val localResult = local.getMovieDetails(movieId = movieId)
        if (localResult is DataResult.Success) {
            emitSuccess(MovieDetailsUiModel(localResult.data))
            return@flow
        }

        val remoteResult = remote.getMovieDetails(movieId = movieId)
        if (remoteResult is DataResult.Success) { // Save to DB
            local.saveMovieDetails(LocalMovie(remoteResult.data))
        }

        when (remoteResult) {
            is DataResult.Success -> emitSuccess(MovieDetailsUiModel(remoteResult.data))
            is DataResult.Failure -> emitFailure(remoteResult.ex)
        }
    }

    override suspend fun saveMovieRating(movieId: String, rating: Float) {
        local.saveMovieRating(movieId, rating)
    }


}
