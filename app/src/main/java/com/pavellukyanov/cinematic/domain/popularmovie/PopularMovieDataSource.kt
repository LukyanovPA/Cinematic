package com.pavellukyanov.cinematic.domain.popularmovie

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.utils.Page
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PopularMovieDataSource @Inject constructor(
    private val popularMovieRepo: PopularMovieRepo
) : RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val nextPage = params.key ?: Page.STARTING_PAGE

        return popularMovieRepo.getPopularMovie(nextPage)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Movie>> { result ->
                LoadResult.Page(
                    data = result,
                    prevKey = if (nextPage == Page.STARTING_PAGE) null else nextPage - 1,
                    nextKey = if (result.isEmpty()) null else nextPage + 1
                )
            }
            .onErrorReturn { e ->
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }
}