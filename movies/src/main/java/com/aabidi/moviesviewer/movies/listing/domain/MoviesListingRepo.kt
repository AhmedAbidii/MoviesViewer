package com.aabidi.moviesviewer.movies.listing.domain

import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.emitFailure
import com.aabidi.moviesviewer.core.extensions.emitSuccess
import com.aabidi.moviesviewer.core.networking.DataResult
import com.aabidi.moviesviewer.core.pagination.Pagination
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import kotlinx.coroutines.flow.flow

class MoviesListingRepo(private val remote: MoviesListingContract.Remote) : MoviesListingContract.Repository {

    private var currentPage: Int = 0
    private var dataItems: MutableList<MovieUiModel> = mutableListOf()

    override suspend fun getMovies() =
        flow<StateResult<Pagination.Result<MovieUiModel>>> {
            val nextPage = currentPage + 1

            when (val apiResult = remote.getPopularMovies(nextPage)) {
                is DataResult.Success -> {
                    val movieUiModels = apiResult.data.results.map { MovieUiModel(it) }
                    currentPage = apiResult.data.page
                    dataItems.addAll(movieUiModels)

                    // Post Pagination Success
                    emitSuccess(
                        Pagination.Result.Append(
                            allElements = dataItems,
                            newPage = movieUiModels
                        )
                    )
                }

                is DataResult.Failure -> emitFailure(apiResult.ex)
            }
        }
}
