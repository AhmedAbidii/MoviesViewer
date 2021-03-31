package com.aabidi.moviesviewer.data.movies.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import java.util.*

@Entity(tableName = "Movie")
data class LocalMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: Date?,
    var rating : Float
) {

    constructor(remoteMovie: RemoteMovie) : this(
        id = remoteMovie.id,
        title = remoteMovie.title,
        overview = remoteMovie.overview,
        backdropPath = remoteMovie.backdropPath.orEmpty(),
        posterPath = remoteMovie.posterPath,
        releaseDate = remoteMovie.releaseDate,
        rating = 0f
    )
}
