package com.pavellukyanov.cinematic.ui.nowplaying

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.databinding.FragmentNowPlayingBinding
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants

fun FragmentNowPlayingBinding.bindAdapter(
    context: Context,
    movieListAdapter: MovieListAdapter
) {
    recyNowPlaying.apply {
        adapter = movieListAdapter
        layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
    }
}