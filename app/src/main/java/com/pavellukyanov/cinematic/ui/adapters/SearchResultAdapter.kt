package com.pavellukyanov.cinematic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavellukyanov.cinematic.databinding.ItemSearchResultBinding
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.utils.load

class SearchResultAdapter(
    private var listItem: MutableList<SearchItem>
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = listItem.size

    private fun getItem(position: Int) = listItem[position]

    fun addItems(items: List<SearchItem>) {
        listItem = items.toMutableList()
    }

    fun clearAdapter() = listItem.clear()

    class SearchResultViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem) {
            with(binding) {
                image.load(item.image, itemView.context)
                itemTitle.text = item.title
            }
        }
    }
}