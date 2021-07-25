package com.pavellukyanov.cinematic.ui.moviedetails

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMovieDetailsBinding
import com.pavellukyanov.cinematic.domain.models.Crew
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import com.pavellukyanov.cinematic.ui.adapters.CastsAdapter
import com.pavellukyanov.cinematic.ui.adapters.MovieDetailsGenresAdapter

fun FragmentMovieDetailsBinding.bindMovieDetails(
    movie: MovieDetails,
    context: Context,
    activity: FragmentActivity?
) {
    val genresAdapter = MovieDetailsGenresAdapter(listOf())
    recyGenres.apply {
        adapter = genresAdapter
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    genresAdapter.apply {
        addGenres(movie.details.genres)
        notifyDataSetChanged()
    }

    Glide.with(context)
        .load(movie.details.posterPath)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(movieDetailsPoster)

    buttonBack.setOnClickListener { activity?.onBackPressed() }
    movieDetailsTitle.text = movie.details.title
    release.text =
        context.getString(
            R.string.release,
            movie.details.releaseDate.substringBefore('-')
        )
    director.text =
        context.getString(
            R.string.director,
            getDirector(movie.crew)
        )
    ratingBarDetails.rating = (movie.details.voteAverage / 2).toFloat()
    movieDescription.text = movie.details.overview

    val castsAdapter = CastsAdapter(listOf())
    recyCasts.apply {
        adapter = castsAdapter
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    castsAdapter.apply {
        addCasts(movie.actors)
        notifyDataSetChanged()
    }
}

private fun getDirector(list: List<Crew>): String {
    list.forEach {
        if (it.job == "Director") {
            return it.name
        }
    }
    return ""
}