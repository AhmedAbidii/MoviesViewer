package com.aabidi.moviesviewer.movies.details.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.aabidi.moviesviewer.core.domain.StateResult
import com.aabidi.moviesviewer.core.extensions.*
import com.aabidi.moviesviewer.core.ui.BaseFragment
import com.aabidi.moviesviewer.core.utils.showToast
import com.aabidi.moviesviewer.movies.R
import com.aabidi.moviesviewer.movies.commons.util.getBackdropImageTransitionName
import com.aabidi.moviesviewer.movies.commons.util.getBackgroundTransitionName
import com.aabidi.moviesviewer.movies.details.di.MovieDetailsModule
import com.aabidi.moviesviewer.movies.details.model.MovieDetailsUiModel
import com.aabidi.moviesviewer.movies.details.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    override val fragmentTag = TAG

    private val viewModel: MovieDetailsViewModel by viewModel()
    private val navArgs: MovieDetailsFragmentArgs by navArgs()
    private var movieId: String? = null

    override fun loadDependencies() = MovieDetailsModule.load()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPosterHeight()
        setupForInsets()
        setupInitialUi()
        fetchData()
    }

    private fun setupInitialUi() {
        toolbarId.setNavigationOnClickListener { requireActivity().onBackPressed() }

        navArgs.movieUiModel.let { uiModel ->
            movieId = uiModel.id
            uiModel.backdropImage.let { backdropImageUrl ->
                if (backdropImageUrl.isNotEmpty()) ivBackdrop.run {
                    movieId?.let {
                        transitionName = getBackdropImageTransitionName(it)
                    }

                    setImage(
                        imageUrl = backdropImageUrl,
                        fadeIn = false,
                        scaleType = ScaleType.CENTER_CROP
                    )
                }
            }

            movieId?.let {
                clBackground.transitionName = getBackgroundTransitionName(it)
            }
        }
    }

    private fun setPosterHeight() = ivPoster.doOnPreDraw {
        it.setDimensions(newHeight = (it.measuredWidth * ASPECT_RATIO).roundToInt())
    }

    private fun setupForInsets() {
        nsvParent.setOnApplyWindowInsetsListener { _, insets ->
            vNavigationBarScrollSpace.updateLayoutParams {
                height = insets.systemWindowInsetBottom
            }
            return@setOnApplyWindowInsetsListener insets
        }

        nsvParent.requestApplyInsets()
    }

    private fun setupListeners() {
        observe(viewModel.movieDetails) {
            when (it) {
                is StateResult.Loading -> showLoading(true)

                is StateResult.Success -> {
                    showLoading(false)
                    setData(it.data)
                }

                is StateResult.Failure -> showError()
            }
        }
    }

    private fun fetchData() {
        viewModel.getMovieDetails(movieId = navArgs.movieUiModel.id)
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .addListener(onTransitionEndListener())
    }

    private fun onTransitionEndListener() = object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            super.onTransitionEnd(transition)
            setupListeners()
        }
    }

    private fun showError() {
        showToast("Error while loading")
    }

    private fun showLoading(isLoading: Boolean) {
        cpbMovie.isVisible = isLoading
    }

    private fun setData(uiModel: MovieDetailsUiModel) = with(uiModel) {
        tvTitle.text = buildTitleWithReleaseDate(uiModel)
        tvOverviewDesc.text = description
        contextNonNull.getBitmapDrawable(backdropImage) { extractDarkColorAndCircularReveal(it) }

        ivPoster.setImage(
            imageUrl = posterImage,
            scaleType = ScaleType.FIT_CENTER
        )

        groupDetails.isVisible = true

        rating_bar_view.rating = uiModel.rating
        rating_bar_view.onRatingBarChangeListener = OnRatingBarChangeListener { _, rating, _ ->
            rateMovie(rating)
        }
    }

    private fun extractDarkColorAndCircularReveal(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            if (palette == null) return@generate
            val defaultColor = colorFrom(R.color.colorPrimaryDark)
            circularReveal(palette.getLightMutedColor(defaultColor))
        }
    }

    private fun circularReveal(newColor: Int) {
        crNewBackground.setBackgroundColor(newColor)

        ViewAnimationUtils.createCircularReveal(
            crNewBackground,
            ivBackdrop.measuredWidth / 2,
            ivBackdrop.bottom,
            0f,
            nsvParent.measuredHeight.toFloat()
        )
            .setDuration(resources.getInteger(android.R.integer.config_longAnimTime).toLong())
            .start()
    }

    private fun buildTitleWithReleaseDate(uiModel: MovieDetailsUiModel) = buildSpannedString {
        bold { append(uiModel.title) }

        if (uiModel.releaseYear.isEmpty()) return@buildSpannedString

        append(" ")

        scale(RELEASE_YEAR_RELATIVE_SIZE) {
            color(colorFrom(R.color.text_secondary)) {
                append(getString(R.string.movie_details_year, uiModel.releaseYear))
            }
        }
    }

    private fun rateMovie(rating: Float) {
        movieId?.let { viewModel.saveMovieRating(it, rating) }
        showToast("Rating Saved")
    }

    companion object {
        const val TAG = "MovieDetailsFragment"

        private const val ASPECT_RATIO = 1.5
        private const val RELEASE_YEAR_RELATIVE_SIZE = 0.8f
    }
}
