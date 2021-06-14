package com.pavellukyanov.cinematic.data.repository.upcoming

import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity
import com.pavellukyanov.cinematic.data.repository.movie.toMovie
import com.pavellukyanov.cinematic.domain.models.Movie

fun List<UpcomingEntity>.comparison(
    listMovie: List<MovieEntity>,
    upcoming: Int
): List<Movie> {
    val finallyList = mutableListOf<Movie>()
    this.forEach { popular ->
        listMovie.forEach { movie ->
            if (movie.id == popular.movieId) {
                finallyList.add(
                    movie.toMovie(upcoming)
                )
            }
        }
    }
    return finallyList
}