package com.aabidi.moviesviewer.movies.commons.business

/**
 * Builds the full path for TMDB movies.
 */
fun buildImagePath(relativePath: String?) =
    if (relativePath.isNullOrEmpty()) null
    else "https://image.tmdb.org/t/p/w780$relativePath"

