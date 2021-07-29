package com.pavellukyanov.cinematic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pavellukyanov.cinematic.databinding.ItemSearchResultBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.utils.load

class SearchResultAdapter(
    diffCallback: DiffUtil.ItemCallback<Movie>,
    private val movieItemClickListener: MovieItemClickListener
) : PagingDataAdapter<Movie, SearchResultAdapter.SearchResultViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            item?.let { movie ->
                movieItemClickListener.onItemClicked(movie.id)
            }
        }
    }

    class SearchResultViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            with(binding) {
                with(item) {
                    image.load(posterPath)
                    itemTitle.text = title
                    itemReleaseDate.text = releaseDate?.substringBefore('-')
                    if (voteAverage != 0.0) {
                        itemVoteCount.text = voteAverage.toString()
                    } else {
                        voteStar.isVisible = false
                        itemVoteCount.isVisible = false
                    }
                }
            }
        }
    }
}