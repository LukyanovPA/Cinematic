package com.pavellukyanov.cinematic.ui.popularmovie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.cinematic.databinding.ViewHolderPopularMovieBinding
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import javax.inject.Inject

class PopularMovieAdapter @Inject constructor(
    diffCallback: DiffUtil.ItemCallback<PopularMovie>
) : PagingDataAdapter<PopularMovie, PopularMovieAdapter.PopularMovieViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val binding =
            ViewHolderPopularMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PopularMovieViewHolder(binding)
    }

    class PopularMovieViewHolder(
        private val binding: ViewHolderPopularMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: PopularMovie?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(movie?.posterPath)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(moviePoster)

                movieTitle.text = movie?.title
            }
        }
    }
}