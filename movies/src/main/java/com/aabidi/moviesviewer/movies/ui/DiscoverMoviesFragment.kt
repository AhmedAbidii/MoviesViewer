package com.aabidi.moviesviewer.movies.ui

import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.aabidi.moviesviewer.core.ui.BaseFragment
import com.aabidi.moviesviewer.movies.R
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import com.aabidi.moviesviewer.movies.details.ui.MovieDetailsFragmentArgs
import com.aabidi.moviesviewer.movies.listing.ui.MoviesListingFragment

class DiscoverMoviesFragment : BaseFragment(R.layout.fragment_discover_movies),
    MoviesListingFragment.MoviesListingInteractions {

    override val fragmentTag = TAG

    companion object {
        const val TAG = "DiscoverMoviesFragment"
    }

    override fun onMovieSelected(model: MovieUiModel, navigatorExtras: FragmentNavigator.Extras) {
        findNavController().navigate(
            R.id.navigateFromHomeToMovieDetails,
            MovieDetailsFragmentArgs(model).toBundle(),
            null,
            navigatorExtras
        )
    }
}
