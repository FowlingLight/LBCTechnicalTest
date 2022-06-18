package com.example.lbctechnicaltest.utils

import android.util.Log
import android.widget.ImageView
import com.example.lbctechnicaltest.R
import com.squareup.picasso.*

fun doNothing() {
    //does literally nothing
}

fun loadImageWithCache(
    url: String,
    imageView: ImageView
) {
    Picasso.get().load(url)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .error(R.drawable.ic_broken_image_24)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                doNothing()
            }

            override fun onError(e: Exception?) {
                //Try again online if cache failed
                Picasso.get()
                    .load(url)
                    .error(R.drawable.ic_broken_image_24)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            doNothing()
                        }

                        @Override
                        override fun onError(e: Exception?) {
                            Log.v("Picasso", "Could not fetch image")
                        }
                    })
            }
        })
}