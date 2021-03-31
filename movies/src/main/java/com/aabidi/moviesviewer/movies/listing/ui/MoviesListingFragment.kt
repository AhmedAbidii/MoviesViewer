package com.aabidi.moviesviewer.movies.listing.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.updatePadding
import androidx.navigation.fragment.FragmentNavigator
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.observe
import com.aabidi.moviesviewer.core.pagination.Pagination
import com.aabidi.moviesviewer.core.ui.BaseFragment
import com.aabidi.moviesviewer.core.utils.showToast
import com.aabidi.moviesviewer.movies.R
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import com.aabidi.moviesviewer.movies.listing.di.SectionMoviesModule
import com.aabidi.moviesviewer.movies.listing.viewmodel.MoviesListingViewModel
import kotlinx.android.synthetic.main.fragment_movies_listing.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListingFragment : BaseFragment(R.layout.fragment_movies_listing),
    MoviesListingAdapter.MoviesListingInteractions {

    override val fragmentTag = TAG

    private val listingViewModel: MoviesListingViewModel by viewModel()
    private val moviesListingAdapter = MoviesListingAdapter(this)
    private lateinit var interactions: MoviesListingInteractions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactions = when {
            context is MoviesListingInteractions -> context
            parentFragment?.parentFragment is MoviesListingInteractions -> parentFragment?.parentFragment as MoviesListingInteractions
            else -> throw IllegalArgumentException("Parent activity or fragment must implement MoviesListingInteractions")
        }
    }

    override fun loadDependencies() = SectionMoviesModule.load()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyStatusBarInsets(view)
        setupAdapter()
        setupListeners()
        fetchData()
    }

    override fun onMovieSelected(model: MovieUiModel, navigatorExtras: FragmentNavigator.Extras) =
        interactions.onMovieSelected(model, navigatorExtras)

    override fun onNewRequest(request: Pagination.Request) = listingViewModel.getMoreMovies()

    override fun onReplacedData() = rvDiscover.scheduleLayoutAnimation()


    private fun applyStatusBarInsets(rootView: View) = with(rootView) {
        setOnApplyWindowInsetsListener { _, windowInsets ->
            updatePadding(top = windowInsets.systemWindowInsetTop)
            return@setOnApplyWindowInsetsListener windowInsets
        }
        requestApplyInsets()
    }

    private fun setupAdapter() {
        rvDiscover.adapter = moviesListingAdapter
    }

    private fun setupListeners() {
        observe(listingViewModel.discoverMovies) {
            when (it) {

                is StateResult.Loading -> {
                    moviesListingAdapter.showLoading()
                }

                is StateResult.Success -> {
                    moviesListingAdapter.setResult(it.data)
                }

                is StateResult.Failure -> showError(it.ex)
            }
        }
    }

    private fun fetchData() = listingViewModel.getMovies()


    private fun showError(ex: Throwable) {
        Log.d(TAG, "Error ${ex.localizedMessage}")
        showToast("error, try again later")
    }

    companion object {
        const val TAG = "MoviesListingFragment"
    }

    interface MoviesListingInteractions {
        fun onMovieSelected(model: MovieUiModel, navigatorExtras: FragmentNavigator.Extras)
    }
}
