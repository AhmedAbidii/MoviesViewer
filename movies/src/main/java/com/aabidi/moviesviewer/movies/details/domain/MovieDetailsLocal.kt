package com.aabidi.moviesviewer.movies.details.domain

import com.aabidi.moviesviewer.core.extensions.toDataResult
import com.aabidi.moviesviewer.data.movies.local.dao.MovieDao
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie

class MovieDetailsLocal(private val movieDao: MovieDao) : MovieDetailsContract.Local {

    override suspend fun getMovieDetails(movieId: String) = movieDao.getMovieDetails(movieId).toDataResult()

    override suspend fun saveMovieDetails(movie: LocalMovie) = movieDao.insert(movie)

    override suspend fun saveMovieRating(movieId: String, rating: Float) {
        movieDao.getMovieDetails(movieId).apply {
            this?.rating = rating
        }?.also {
            movieDao.update(it)
        }
    }
}
