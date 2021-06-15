package com.pavellukyanov.cinematic.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter
import com.pavellukyanov.cinematic.ui.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val genresAdapter by lazy { GenresListAdapter(listOf()) }
    private var _binding: FragmentMainBinding? = null
    private lateinit var pageAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
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
            recyGenres.apply {
                adapter = genresAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun subscribeViewModel() {
        mainViewModel.getAllGenres().observe(viewLifecycleOwner, (this::onStateReceiveGenres))
    }

    private fun onStateReceiveGenres(resourceState: ResourceState<List<Genre>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateGenres(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateGenres(true)
            is ResourceState.Error -> handleErrorStateGenres(resourceState.error)
        }
    }

    private fun handleSuccessStateGenres(data: List<Genre>) {
        handleLoadingStateGenres(false)
        genresAdapter.apply {
            addGenres(data)
            notifyDataSetChanged()
        }
    }

    private fun handleLoadingStateGenres(state: Boolean) {

    }

    private fun handleErrorStateGenres(error: Throwable?) {
        handleLoadingStateGenres(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainViewModel.onDestroy()
    }
}