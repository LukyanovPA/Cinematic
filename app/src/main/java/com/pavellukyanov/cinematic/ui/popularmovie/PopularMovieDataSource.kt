package com.pavellukyanov.cinematic.ui.popularmovie

import androidx.paging.PageKeyedDataSource
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import javax.inject.Inject

class PopularMovieDataSource @Inject constructor(
    private val popularMovieRepo: PopularMovieRepo
) : PageKeyedDataSource<Int, PopularMovie>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PopularMovie>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovie>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovie>) {
        TODO("Not yet implemented")
    }

    private fun executeQuery(page: Int, perPage: Int, callback:(List<PopularMovie>) -> Unit) {
            //val users = repository.searchUsersWithPagination(query, page, perPage, sort)
            //callback(users)
    }

}