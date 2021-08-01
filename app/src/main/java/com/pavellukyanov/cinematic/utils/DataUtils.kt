package com.pavellukyanov.cinematic.utils

import androidx.recyclerview.widget.DiffUtil
import com.pavellukyanov.cinematic.domain.models.Movie
import java.time.LocalDate
import java.time.Period


object PosterSizeList {
    var posterSizes: List<String>? = emptyList()
}

enum class PosterSizes(val size: String?) {
    W45(PosterSizeList.posterSizes?.get(0)),
    W92(PosterSizeList.posterSizes?.get(1)),
    W154(PosterSizeList.posterSizes?.get(2)),
    W185(PosterSizeList.posterSizes?.get(3)),
    W300(PosterSizeList.posterSizes?.get(4)),
    W500(PosterSizeList.posterSizes?.get(5)),
    ORIGINAL(PosterSizeList.posterSizes?.get(6))
}

object ProfileSizeList {
    var profileSizes: List<String?> = emptyList()
}

enum class ProfileSize(val size: String?) {
    W45(ProfileSizeList.profileSizes[0]),
    W185(ProfileSizeList.profileSizes[1]),
    H362(ProfileSizeList.profileSizes[2]),
    ORIGINAL(ProfileSizeList.profileSizes[3])
}

object MovieComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return when (oldItem.id) {
            newItem.id -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return when {
            oldItem.id == newItem.id -> true
            oldItem.isUpcoming == newItem.isUpcoming -> true
            oldItem.releaseDate == newItem.releaseDate -> true
            oldItem.title == newItem.title -> true
            oldItem.posterPath == newItem.posterPath -> true
            oldItem.voteAverage == newItem.voteAverage -> true
            else -> false
        }
    }
}

const val APP_METRICA_KEY = "43581778-9841-47dd-9722-dc60518cb069"


object DateHelper {
    fun getAge(birthday: String, deathday: String?): Int {
        val yearBirthday = birthday.substringBefore('-').toInt()
        val monthBirthday = birthday.substringAfter('-').substringBefore('-').toInt()
        val dayBirthday = birthday.substring(8).toInt()
        val startBirthday = LocalDate.of(yearBirthday, monthBirthday, dayBirthday)

        return if (deathday != null) {
            val yearDeathday = deathday.substringBefore('-').toInt()
            val monthDeathday = deathday.substringAfter('-').substringBefore('-').toInt()
            val dayDeathday = deathday.substring(8).toInt()

            val endDate = LocalDate.of(yearDeathday, monthDeathday, dayDeathday)

            Period.between(startBirthday, endDate).years
        } else {
            Period.between(startBirthday, LocalDate.now()).years
        }
    }
}