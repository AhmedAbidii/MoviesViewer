package com.aabidi.moviesviewer.movies.details.model

import com.aabidi.moviesviewer.core.extensions.yearAsString
import com.aabidi.moviesviewer.core.utils.emptyString
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import com.aabidi.moviesviewer.movies.commons.business.buildImagePath
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie

data class MovieDetailsUiModel(
    val title: String,
    val description: String,
    val backdropImage: String,
    val posterImage: String,
    val releaseYear: String,
    val rating: Float
) {

    constructor(remoteMovie: RemoteMovie) : this(
        title = remoteMovie.title,
        description = remoteMovie.overview,
        backdropImage = buildImagePath(remoteMovie.backdropPath).orEmpty(),
        posterImage = buildImagePath(remoteMovie.posterPath).orEmpty(),
        releaseYear = remoteMovie.releaseDate?.yearAsString() ?: emptyString(),
        rating = 0f
    )

    constructor(movie: LocalMovie) : this(
        title = movie.title,
        description = movie.overview,
        backdropImage = buildImagePath(movie.backdropPath).orEmpty(),
        posterImage = buildImagePath(movie.posterPath).orEmpty(),
        releaseYear = movie.releaseDate?.yearAsString() ?: emptyString(),
        rating = movie.rating
    )
}
