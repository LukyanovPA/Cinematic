package com.pavellukyanov.cinematic.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.SearchResultAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.MovieComparator
import com.pavellukyanov.cinematic.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : BaseFragment<List<Genre>, MainViewModel>(R.layout.fragment_main) {
    private val vm: MainViewModel by viewModels()
    private val searchAdapter by lazy {
        SearchResultAdapter(
            MovieComparator,
            movieItemClickListener
        )
    }
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        initUi()
        onSubscribeViewModel(vm)
        vm.getAllGenres()
        onSearch()
    }

    private fun initUi() {
        with(binding) {
            search(vm)
            searchBar.setOnSearchClickListener {
                isSearchVisible(true)
            }

            searchBar.setOnCloseListener {
                isSearchVisible(false)
                hideKeyboard()
                false
            }

            mainPager.bind(
                tabLayout,
                requireContext(),
                layoutInflater,
                childFragmentManager,
                viewLifecycleOwner.lifecycle
            )
        }
    }

    private fun onSearch() {
        vm.getSearchResult().observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResourceState.Success -> {
                    with(binding) {
                        mainSearchResult.bind(
                            searchAdapter,
                            result.data,
                            requireContext(),
                            lifecycle
                        )
                    }
                }
                is ResourceState.Loading -> {
                    //TODO()
                }
                is ResourceState.Error -> {
                    //TODO()
                }
            }
        })
    }

    override fun handleSuccessState(data: List<Genre>) {
        super.handleSuccessState(data)
        binding.bindAdapter(data, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.onDestroy()
        _binding = null
        vm.onDestroy()
    }
}
