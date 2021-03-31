package com.aabidi.moviesviewer.movies.commons.model

import android.os.Parcelable
import com.aabidi.moviesviewer.core.extensions.toReadableDate
import com.aabidi.moviesviewer.core.pagination.PaginatedItem
import com.aabidi.moviesviewer.core.utils.emptyString
import com.aabidi.moviesviewer.data.movies.remote.api.MovieListing
import com.aabidi.moviesviewer.movies.commons.business.buildImagePath
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUiModel(
    val id: String,
    val title: String,
    val backdropImage: String,
    val releaseDate: String
) : PaginatedItem, Parcelable {

    constructor(movie: MovieListing) : this(
        id = movie.id.toString(),
        title = movie.title,
        backdropImage = buildImagePath(movie.backdropPath).orEmpty(),
        releaseDate = movie.releaseDate?.toReadableDate() ?: emptyString()
    )
}
