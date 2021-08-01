package com.pavellukyanov.cinematic.utils

import android.os.Build
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(birthday: String): Int {
        val year = birthday.substringBefore('-').toInt()
        val month = birthday.substringAfter('-').substringBefore('-').toInt()
        val day = birthday.substring(8).toInt()
        val age = LocalDate.of(year, month, day)
        return Period.between(age, LocalDate.now()).years
    }
}