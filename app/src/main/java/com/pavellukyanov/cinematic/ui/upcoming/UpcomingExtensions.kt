package com.pavellukyanov.cinematic.ui.upcoming

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.databinding.FragmentUpcomingBinding
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants

fun FragmentUpcomingBinding.bindAdapter(
    context: Context,
    movieListAdapter: MovieListAdapter
) {
    recyUpcoming.apply {
        adapter = movieListAdapter
        layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
    }
}