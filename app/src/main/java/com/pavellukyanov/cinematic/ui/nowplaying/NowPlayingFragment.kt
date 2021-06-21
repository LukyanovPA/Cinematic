package com.pavellukyanov.cinematic.ui.nowplaying

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentNowPlayingBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieItemClickListener
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.ui.main.MainFragmentDirections
import com.pavellukyanov.cinematic.utils.Constants
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : BaseFragment<PagingData<Movie>>(R.layout.fragment_now_playing) {
    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NowPlayingViewModel by viewModels()
    private val popAdapter by lazy { MovieListAdapter(MovieComparator, movieItemClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        binding.bindAdapter(requireContext(), popAdapter)
    }

    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: PagingData<Movie>) {
        super.handleSuccessStateMovies(data)
        popAdapter.submitData(lifecycle, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}