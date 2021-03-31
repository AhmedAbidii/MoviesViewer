package com.aabidi.moviesviewer.movies.listing.ui

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.aabidi.moviesviewer.core.extensions.*
import com.aabidi.moviesviewer.core.pagination.BottomPaginationAdapter
import com.aabidi.moviesviewer.core.pagination.PaginationListener
import com.aabidi.moviesviewer.core.pagination.PaginationViewHolder
import com.aabidi.moviesviewer.movies.R
import com.aabidi.moviesviewer.movies.commons.model.MovieUiModel
import com.aabidi.moviesviewer.movies.commons.util.getBackdropImageTransitionName
import com.aabidi.moviesviewer.movies.commons.util.getBackgroundTransitionName
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesListingAdapter(private val interactions: MoviesListingInteractions) :
    BottomPaginationAdapter<MovieUiModel>(interactions) {

    override fun onCreateVH(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        parent.inflate(R.layout.item_movie)
    )

    override fun onBindVH(holder: PaginationViewHolder, position: Int) {
        if (holder is MovieViewHolder) holder.bind(dataItems[position], interactions)
    }

    override fun areItemsSame(oldItem: MovieUiModel, newItem: MovieUiModel) =
        oldItem.id == newItem.id

    class MovieViewHolder(rootView: View) : PaginationViewHolder(rootView) {

        fun bind(uiModel: MovieUiModel, interactions: MoviesListingInteractions): ConstraintLayout = with(itemView) {
            val movieId = uiModel.id
            val backgroundTransitionName = getBackgroundTransitionName(movieId)
            val backdropTransitionName = getBackdropImageTransitionName(movieId)

            ivBackdrop.apply {
                transitionName = backdropTransitionName
                setImage(
                    uiModel.backdropImage,
                    scaleType = ScaleType.CENTER_CROP,
                    onFailure = { ivBackdrop.defaultImage() },
                    fadeIn = false
                )
            }

            tvTitle.text = uiModel.title
            tvRelease.setTextOrGone(uiModel.releaseDate)

            clRoot.apply {
                transitionName = backgroundTransitionName
                setOnClickListener {
                    interactions.onMovieSelected(
                        uiModel, FragmentNavigatorExtras(
                            ivBackdrop to backdropTransitionName,
                            clRoot to backgroundTransitionName
                        )
                    )
                }
            }
        }
    }

    interface MoviesListingInteractions : PaginationListener {
        fun onMovieSelected(model: MovieUiModel, navigatorExtras: FragmentNavigator.Extras)
    }
}
