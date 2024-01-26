package com.example.infopulse.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.uploadImage(url: String) {
    Glide.with(this).load(url).into(this)
}