package com.pavellukyanov.cinematic.utils

import androidx.recyclerview.widget.DiffUtil
import com.pavellukyanov.cinematic.domain.models.Movie
import java.text.SimpleDateFormat
import java.util.*


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

//Доделать!!
object DateHelper {
    private val locale = Locale("ru")
    private val c = Calendar.getInstance(locale)
    private var year = c.get(Calendar.YEAR)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val currentTime = Date(System.currentTimeMillis())

    fun getAge(birthday: String): Int {
        val birthdayDate = dateFormat.parse(birthday)
        return Date(currentTime.time - birthdayDate.time).year
    }
}