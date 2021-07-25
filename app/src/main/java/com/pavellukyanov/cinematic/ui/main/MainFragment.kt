package com.pavellukyanov.cinematic.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<List<Genre>>(R.layout.fragment_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        initUi()
        subscribeViewModel()
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

    private fun subscribeViewModel() {
        mainViewModel.getAllGenres().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: List<Genre>) {
        super.handleSuccessStateMovies(data)
        binding.bindAdapter(data, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainViewModel.onDestroy()
    }
}