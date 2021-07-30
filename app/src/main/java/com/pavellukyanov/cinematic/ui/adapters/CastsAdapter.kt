package com.pavellukyanov.cinematic.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavellukyanov.cinematic.databinding.ItemActorsListBinding
import com.pavellukyanov.cinematic.domain.models.Actor
import com.pavellukyanov.cinematic.utils.load

class CastsAdapter(
    private var actors: List<Actor>,
    private val peopleItemClickListener: PeopleItemClickListener
) : RecyclerView.Adapter<CastsAdapter.CastsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsVH {
        val binding = ItemActorsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CastsVH(binding)
    }

    override fun onBindViewHolder(holder: CastsVH, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            peopleItemClickListener.onItemClicked(getItem(position).id)
        }
    }

    override fun getItemCount(): Int = actors.size

    private fun getItem(position: Int) = actors[position]

    fun addCasts(listActor: List<Actor>) {
        actors = listActor
    }

    class CastsVH(private val binding: ItemActorsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            with(binding) {
                with(actor) {
                    actorPicture.load(profilePath, circleCrop = true)
                    actorName.text = name
                    actorCharacter.text = character
                }
            }
            Log.d("ttt", "actor - ${actor.id}")
        }
    }
}