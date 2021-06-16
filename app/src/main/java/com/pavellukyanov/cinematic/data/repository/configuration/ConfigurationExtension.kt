package com.pavellukyanov.cinematic.data.repository.configuration

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.MovieDetailsResponse
import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits.CreditsResponse
import com.pavellukyanov.cinematic.data.repository.movie.setupMoviePoster
import com.pavellukyanov.cinematic.data.repository.movie.toMovie
import com.pavellukyanov.cinematic.data.repository.moviedetails.credits.setupProfilePoster
import com.pavellukyanov.cinematic.data.repository.moviedetails.credits.toActor
import com.pavellukyanov.cinematic.data.repository.moviedetails.credits.toCrew
import com.pavellukyanov.cinematic.data.repository.moviedetails.setupMoviePoster
import com.pavellukyanov.cinematic.data.repository.moviedetails.toDetails
import com.pavellukyanov.cinematic.domain.models.Actor
import com.pavellukyanov.cinematic.domain.models.Crew
import com.pavellukyanov.cinematic.domain.models.Details
import com.pavellukyanov.cinematic.domain.models.Movie

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

fun ConfigurationResponse.toDetails(
    movieDetailsResponse: MovieDetailsResponse
): Details {
    movieDetailsResponse.setupMoviePoster(
        this.images.posterSizes,
        this.images.baseUrl
    )
    return movieDetailsResponse.toDetails()
}

fun ConfigurationResponse.toListActor(
    creditsResponse: CreditsResponse
): List<Actor> {
    val mapList = mutableListOf<Actor>()
    creditsResponse.cast.forEach { castResponse ->
        castResponse.setupProfilePoster(
            this.images.profileSizes,
            this.images.secureBaseUrl
        )
        mapList.add(castResponse.toActor())
    }
    return mapList
}

fun ConfigurationResponse.toListCrew(
    creditsResponse: CreditsResponse
): List<Crew> {
    val mapList = mutableListOf<Crew>()
    creditsResponse.crew.forEach { crewResponse ->
        crewResponse.setupProfilePoster(
            this.images.profileSizes,
            this.images.secureBaseUrl
        )
        mapList.add(crewResponse.toCrew())
    }
    return mapList
}