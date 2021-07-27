package com.pavellukyanov.cinematic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.cinematic.databinding.ItemActorsListBinding
import com.pavellukyanov.cinematic.domain.models.Actor
import com.pavellukyanov.cinematic.utils.load

class CastsAdapter(
    private var actors: List<Actor>
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
                actorPicture.load(actor.profilePath, itemView.context)

//                Glide.with(itemView.context)
//                    .asBitmap()
//                    .load(actor.profilePath)
//                    .circleCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .into(actorPicture)

                actorName.text = actor.name
                actorCharacter.text = actor.character
            }
        }
    }
}