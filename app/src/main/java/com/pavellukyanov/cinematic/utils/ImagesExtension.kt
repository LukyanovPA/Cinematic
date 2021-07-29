package com.pavellukyanov.cinematic.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.cinematic.R

fun ImageView.load(
    value: String?,
    circleCrop: Boolean = false
) {
    Glide.with(this)
        .asBitmap()
        .load(value)
        .apply {
            if (circleCrop) {
                circleCrop()
            } else {
                centerCrop()
            }
        }
        .placeholder(R.drawable.ic_movie_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}