package com.pavellukyanov.cinematic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavellukyanov.cinematic.databinding.ItemGenreListBinding
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter.GenresListViewHolder

class MovieDetailsGenresAdapter(
    private var genres: List<Genre>
) : RecyclerView.Adapter<GenresListAdapter.GenresListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresListViewHolder {
        val binding = ItemGenreListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenresListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = genres.size

    private fun getItem(position: Int) = genres[position]

    fun addGenres(listGenres: List<Genre>) {
        genres = listGenres
    }
}