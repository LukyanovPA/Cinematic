package com.pavellukyanov.cinematic.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.load(
    value: String?,
    context: Context
) {
    Glide.with(context)
        .asBitmap()
        .load(value)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}