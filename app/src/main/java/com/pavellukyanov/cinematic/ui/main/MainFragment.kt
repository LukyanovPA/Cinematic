package com.pavellukyanov.cinematic.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : BaseFragment<List<Genre>, MainViewModel>(R.layout.fragment_main) {
    private val vm: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        initUi()
        onSubscribeViewModel(vm)
        vm.getAllGenres()
    }

    private fun initUi() {
        with(binding) {
            pager.bindMainViewPager(
                tabLayout,
                requireContext(),
                layoutInflater,
                childFragmentManager,
                viewLifecycleOwner.lifecycle
            )
        }
    }

    override fun handleSuccessState(data: List<Genre>) {
        super.handleSuccessState(data)
        binding.bindAdapter(data, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        vm.onDestroy()
    }
}