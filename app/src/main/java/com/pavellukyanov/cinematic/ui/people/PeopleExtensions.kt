package com.pavellukyanov.cinematic.ui.people

import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.pavellukyanov.cinematic.databinding.FragmentPeopleDetailsBinding
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import com.pavellukyanov.cinematic.utils.load

fun FragmentPeopleDetailsBinding.bind(
    value: PeopleDetails,
    activity: FragmentActivity?
) {
    with(value) {
        personButtonBack.setOnClickListener { activity?.onBackPressed() }
        personPoster.load(profile_path, fit = true)
        personName.text = name
        personAge.text = age
        personBirthday.text = birthday
        if (deathday != null) {
            personDeathdayTitle.isVisible = true
            personDeathday.isVisible = true
            personDeathday.text = deathday
        } else {
            personDeathdayTitle.isVisible = false
            personDeathday.isVisible = false
        }
        personPlaceOfBirthday.text = place_of_birth
        personBio.text = biography
    }
}