package com.pavellukyanov.cinematic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pavellukyanov.cinematic.databinding.ItemMovieListBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.utils.load
import javax.inject.Inject

class MovieListAdapter @Inject constructor(
    diffCallback: DiffUtil.ItemCallback<Movie>,
    private val movieItemClickListener: MovieItemClickListener
) : PagingDataAdapter<Movie, MovieListAdapter.ListMovieViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            item?.let { movie ->
                movieItemClickListener.onItemClicked(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieViewHolder {
        val binding =
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListMovieViewHolder(binding)
    }

    class ListMovieViewHolder(
        private val binding: ItemMovieListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            with(binding) {
                moviePoster.load(movie?.posterPath)
                movieTitle.text = movie?.title
                rating.text = movie?.voteAverage.toString()

                when (movie?.isUpcoming) {
                    1 -> {
                        releaseDate.isVisible = true
                        releaseDate.text = movie.releaseDate
                    }
                    else -> releaseDate.isVisible = false
                }
            }
        }
    }
}
