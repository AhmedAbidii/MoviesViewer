package com.aabidi.moviesviewer.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import com.aabidi.moviesviewer.data.util.BaseDao

@Dao
abstract class MovieDao : BaseDao<LocalMovie> {

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    abstract fun getMovieDetails(movieId: String): LocalMovie?

}
