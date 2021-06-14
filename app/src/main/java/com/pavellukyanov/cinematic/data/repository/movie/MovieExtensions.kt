package com.pavellukyanov.cinematic.data.repository.movie

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.TopRatedEntity
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes

fun MovieResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.W500.size}/$posterPath"
}

fun MovieResponse.toMovie(upcoming: Int) = Movie(
    id = id,
    title = title,
    posterPath = moviePoster,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    isUpcoming = upcoming
)

fun MovieEntity.toMovie(upcoming: Int) = Movie(
    id = id,
    title = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    isUpcoming = upcoming
)

fun Movie.toMovieEntity(upcoming: Int) = releaseDate?.let {
    MovieEntity(
        id = id,
        originalTitle = title,
        posterPath = posterPath,
        releaseDate = it,
        voteAverage = voteAverage,
        isUpcoming = upcoming
    )
}

fun Movie.toUpcoming() = UpcomingEntity(movieId = id)
fun Movie.toTopRated() = TopRatedEntity(movieId = id)
fun Movie.toPopularMovie() = PopularMovieEntity(movieId = id)
fun Movie.toNowPlaying() = NowPlayingEntity(movieId = id)