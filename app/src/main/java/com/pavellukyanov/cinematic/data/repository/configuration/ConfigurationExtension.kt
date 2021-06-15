package com.pavellukyanov.cinematic.data.repository.configuration

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.MovieDetailsResponse
import com.pavellukyanov.cinematic.data.repository.movie.setupMoviePoster
import com.pavellukyanov.cinematic.data.repository.movie.toMovie
import com.pavellukyanov.cinematic.data.repository.moviedetails.setupMoviePoster
import com.pavellukyanov.cinematic.data.repository.moviedetails.toMovieDetails
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.models.MovieDetails

fun ConfigurationResponse.toMovieList(
    listMovieResponse: List<MovieResponse>,
    upcoming: Int
): List<Movie> {
    val mappingList = mutableListOf<Movie>()
    listMovieResponse.forEach { movieResponse ->
        movieResponse.setupMoviePoster(
            this.images.posterSizes,
            this.images.baseUrl
        )
        mappingList.add(movieResponse.toMovie(upcoming))
    }
    return mappingList
}

fun ConfigurationResponse.toMovieDetails(
    movieDetailsResponse: MovieDetailsResponse
): MovieDetails {
    movieDetailsResponse.setupMoviePoster(
        this.images.posterSizes,
        this.images.baseUrl
    )
    return movieDetailsResponse.toMovieDetails()
}