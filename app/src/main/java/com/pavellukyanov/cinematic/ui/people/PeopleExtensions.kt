package com.pavellukyanov.cinematic.ui.people

import androidx.fragment.app.FragmentActivity
import com.pavellukyanov.cinematic.databinding.FragmentPeopleDetailsBinding
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import com.pavellukyanov.cinematic.utils.DateHelper
import com.pavellukyanov.cinematic.utils.load

fun FragmentPeopleDetailsBinding.bind(
    value: PeopleDetails,
    activity: FragmentActivity?
) {
    personButtonBack.setOnClickListener { activity?.onBackPressed() }
    personPoster.load(value.profile_path, fit = true)
    personName.text = value.name
    personAge.append(DateHelper.getAge(value.birthday).toString())
}