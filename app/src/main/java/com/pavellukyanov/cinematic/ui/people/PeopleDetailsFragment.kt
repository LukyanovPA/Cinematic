package com.pavellukyanov.cinematic.ui.people

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentPeopleDetailsBinding
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailsFragment :
    BaseFragment<PeopleDetails, PeopleViewModel>(R.layout.fragment_people_details) {
    private val vm: PeopleViewModel by viewModels()
    private val args: PeopleDetailsFragmentArgs by navArgs()
    private var _binding: FragmentPeopleDetailsBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPeopleDetailsBinding.bind(view)
        onSubscribeViewModel(vm)
        getPeopleDetails(args.id)
    }

    private fun getPeopleDetails(id: Int) {
        vm.getPeopleDetails(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        vm.onDestroy()
    }
}