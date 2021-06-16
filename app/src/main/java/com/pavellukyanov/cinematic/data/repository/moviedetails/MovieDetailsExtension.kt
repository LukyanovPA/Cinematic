package com.pavellukyanov.cinematic.data.repository.moviedetails

import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.MovieDetailsResponse
import com.pavellukyanov.cinematic.data.repository.genres.toListGenre
import com.pavellukyanov.cinematic.domain.models.Actor
import com.pavellukyanov.cinematic.domain.models.Crew
import com.pavellukyanov.cinematic.domain.models.Details
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes

fun MovieDetailsResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.ORIGINAL.size}/$backdropPath"
}

fun MovieDetailsResponse.toDetails() = Details(
    id = id,
    title = title,
    posterPath = moviePoster,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    overview = overview,
    genres = genres.toListGenre()
)

interface MovieDetailsMapper<K, M, T, C> {
    operator fun invoke(k: K, m: M, t: T): C
}

class MovieDetailsMapperImpl :
    MovieDetailsMapper<Details, List<Actor>, List<Crew>, MovieDetails> {
    override fun invoke(k: Details, m: List<Actor>, t: List<Crew>) = MovieDetails(
        k,
        m,
        t
    )
}