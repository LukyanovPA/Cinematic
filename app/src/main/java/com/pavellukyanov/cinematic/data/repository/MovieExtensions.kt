package com.pavellukyanov.cinematic.data.repository.movie

import android.util.Log
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes
import com.pavellukyanov.cinematic.utils.SearchItemType
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

fun MovieResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.W500.size}/$posterPath"
}

fun MovieResponse.toMovie() = Movie(
    id = id,
    title = title,
    posterPath = moviePoster,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun Movie.toMovieEntity() = releaseDate?.let {
    MovieEntity(
        id = id,
        originalTitle = title,
        posterPath = posterPath,
        releaseDate = it,
        voteAverage = voteAverage
    )
}

fun List<Movie>.toListMovieEntity(): List<MovieEntity> {
    val list = mutableListOf<MovieEntity>()
    this.forEach { movie ->
        movie.toMovieEntity()?.let { list.add(it) }
    }
    return list
}

const val LOG = "InsertBD"

fun List<Movie>.insertInDatabase(
    database: MovieDatabase
): Completable {
    return Completable.fromAction {
        this.toListMovieEntity().let { movieEntity ->
            database.movies()
                .insertMovies(movieEntity)
                .subscribeOn(Schedulers.io())
                .doOnError { e ->
                    when (e) {
                        is IOException -> Log.d(LOG, e.localizedMessage!!)
                        is HttpException -> Log.d(LOG, e.localizedMessage!!)
                        else -> throw e
                    }
                }
                .subscribe()
        }
    }
}

fun Movie.toSearchItem() =
    SearchItem(
        this.title,
        this.posterPath,
        SearchItemType.MOVIE
    )