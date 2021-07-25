package com.pavellukyanov.cinematic.ui.popularmovie

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.databinding.FragmentPopularMovieBinding
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants

fun FragmentPopularMovieBinding.bindAdapter(
    context: Context,
    movieListAdapter: MovieListAdapter
) {
    recyPopularMovie.apply {
        adapter = movieListAdapter
        layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
    }
}