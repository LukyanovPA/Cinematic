package com.pavellukyanov.cinematic.data.repository.moviedetails.credits

import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits.CastResponse
import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits.CrewResponse
import com.pavellukyanov.cinematic.domain.models.Actor
import com.pavellukyanov.cinematic.domain.models.Crew
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes
import com.pavellukyanov.cinematic.utils.ProfileSize
import com.pavellukyanov.cinematic.utils.ProfileSizeList

fun CastResponse.setupProfilePoster(profileSizes: List<String>, baseUrl: String) {
    ProfileSizeList.profileSizes = profileSizes
    profilePoster = if (profilePath == null) {
        "https://forum.truckersmp.com/uploads/monthly_2021_04/imported-photo-263413.thumb.png.e4d4062fa72a70e989ab2fb6755c9823.png"
    } else {
        "$baseUrl/${ProfileSize.ORIGINAL.size}/$profilePath"
    }
}

fun CastResponse.toActor() = Actor(
    id = id,
    name = name,
    profilePath = profilePoster,
    character = character
)

fun CrewResponse.setupProfilePoster(profileSizes: List<String>, baseUrl: String) {
    ProfileSizeList.profileSizes = profileSizes
    profilePoster = if (profilePath == null) {
        "https://forum.truckersmp.com/uploads/monthly_2021_04/imported-photo-263413.thumb.png.e4d4062fa72a70e989ab2fb6755c9823.png"
    } else {
        "$baseUrl/${ProfileSize.ORIGINAL.size}/$profilePath"
    }
}

fun CrewResponse.toCrew() = Crew(
    id = id,
    name = name,
    profilePath = profilePoster,
    job = job
)