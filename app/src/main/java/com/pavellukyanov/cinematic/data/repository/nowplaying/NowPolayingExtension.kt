package com.pavellukyanov.cinematic.data.repository.nowplaying

import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import com.pavellukyanov.cinematic.data.repository.movie.toMovie
import com.pavellukyanov.cinematic.domain.models.Movie

fun List<NowPlayingEntity>.comparison(
    listMovie: List<MovieEntity>,
    upcoming: Int
): List<Movie> {
    val finallyList = mutableListOf<Movie>()
    this.forEach { popular ->
        listMovie.forEach { movie ->
            if (movie.id == popular.movieId) {
                finallyList.add(movie.toMovie(upcoming))
            }
        }
    }
    return finallyList
}