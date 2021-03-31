package com.aabidi.moviesviewer.movies.model

import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import com.aabidi.moviesviewer.data.movies.remote.api.RemoteMovie
import com.aabidi.moviesviewer.movies.commons.business.buildImagePath
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests the conversion from Local / Remote models to UiModels
 */
class MovieDetailsUiModelTest {

    @Test
    fun convertRemoteToUiModel() {
        val remoteUiModel = MovieDetailsUiModel(TEST_REMOTE_MOVIE)

        assertEquals("Test Movie", remoteUiModel.title)
        assertEquals("An overview for a Test Movie", remoteUiModel.description)

        assertEquals(
            buildImagePath("https://picsum.photos/200/300"),
            remoteUiModel.backdropImage
        )
        assertEquals(
            buildImagePath("https://picsum.photos/200/300"),
            remoteUiModel.posterImage
        )
        assertEquals("2019", remoteUiModel.releaseYear)
    }

    @Test
    fun convertLocalToUiModel() {
        val localUiModel = MovieDetailsUiModel(TEST_LOCAL_MOVIE)

        assertEquals("Test Movie", localUiModel.title)
        assertEquals("An overview for a Test Movie", localUiModel.description)

        assertEquals(
            buildImagePath("https://picsum.photos/200/300"),
            localUiModel.backdropImage
        )
        assertEquals(
            buildImagePath("https://picsum.photos/200/300"),
            localUiModel.posterImage
        )
        assertEquals("2019", localUiModel.releaseYear)
    }

    companion object {

        private val TEST_DATE = Calendar.getInstance().apply { set(Calendar.YEAR, 2019) }.time

        private val TEST_REMOTE_MOVIE = RemoteMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = TEST_DATE
        )

        private val TEST_LOCAL_MOVIE = LocalMovie(
            id = 2,
            title = "Test Movie",
            overview = "An overview for a Test Movie",
            backdropPath = "https://picsum.photos/200/300",
            posterPath = "https://picsum.photos/200/300",
            releaseDate = TEST_DATE,
            rating = 0f
        )

    }
}
