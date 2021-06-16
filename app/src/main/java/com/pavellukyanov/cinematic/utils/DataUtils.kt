package com.pavellukyanov.cinematic.utils

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