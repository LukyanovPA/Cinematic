package com.pavellukyanov.cinematic.ui.toprated

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.databinding.FragmentTopRatedBinding
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants

fun FragmentTopRatedBinding.bindAdapter(
    context: Context,
    movieListAdapter: MovieListAdapter
) {
    recyTopRated.apply {
        adapter = movieListAdapter
        layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
    }
}