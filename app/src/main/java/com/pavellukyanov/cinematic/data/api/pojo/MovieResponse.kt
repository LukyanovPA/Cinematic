package com.pavellukyanov.cinematic.data.api.pojo

import com.google.gson.annotations.SerializedName
import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes

data class MovieResponse(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdropPath: String,
    @SerializedName("genre_ids") var genreIds: List<Int>,
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("title") var title: String,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Int
) {
    var moviePoster = ""
}

fun MovieResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.W500.size}/$posterPath"
}

fun MovieResponse.toPopularMovie() = PopularMovie(
    id = id,
    title = title,
    posterPath = moviePoster,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieResponse.toPopularMovieEntity() = releaseDate?.let {
    PopularMovieEntity(
        id = id,
        originalTitle = title,
        posterPath = moviePoster,
        releaseDate = it,
        voteAverage = voteAverage
    )
}
