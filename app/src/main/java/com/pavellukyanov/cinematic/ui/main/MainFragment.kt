package com.pavellukyanov.cinematic.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.SearchResultAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : BaseFragment<List<Genre>, MainViewModel>(R.layout.fragment_main) {
    private val vm: MainViewModel by viewModels()
    private val searchAdapter by lazy { SearchResultAdapter(emptyList()) }
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
            searchBar.search()
                .debounce(0, TimeUnit.MICROSECONDS)
                .filter { text -> text.isNotEmpty() }
                .distinctUntilChanged()
//                .switchMap(object : Function<String, ObservableSource<String>> {
//                    override fun apply(t: String): ObservableSource<String> {
//                        return vm.search(t)
//                    }
//                })
            // доделать https://habr.com/ru/post/345278/

            onSearch(searchBar.isActivated)
        }
    }

    private fun onSearch(state: Boolean) {
        binding.searchResult.isVisible = state
        searchAdapter.apply {
            addItems(vm.getSearchingResult())
            notifyDataSetChanged()
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
