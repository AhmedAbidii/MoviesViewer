package com.aabidi.moviesviewer.data.movies.remote

import com.aabidi.moviesviewer.data.movies.remote.api.MovieListingEnvelope
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieWebService {

    @GET("/3/movie/{movieId}")
    suspend fun getMovieAsync(@Path("movieId") movieId: String): RemoteMovie

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") requestPage: Int): MovieListingEnvelope

}
