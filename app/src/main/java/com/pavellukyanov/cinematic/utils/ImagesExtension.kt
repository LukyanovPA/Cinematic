package com.pavellukyanov.cinematic.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}