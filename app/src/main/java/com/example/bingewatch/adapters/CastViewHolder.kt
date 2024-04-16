package com.example.bingewatch.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.bingewatch.R
import com.example.bingewatch.models.Cast
import com.example.bingewatch.util.Constants

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val profileImage: ImageView = itemView.findViewById(R.id.image_cast)
    private val nameTextView: TextView = itemView.findViewById(R.id.cast_name)

    fun bind(cast: Cast) {
        Glide.with(itemView.context)
            .load(Constants.IMAGE_BASE_URL+cast.profilePath)
            .transform(CircleCrop())
            .placeholder(R.drawable.user)
            .into(profileImage)

        nameTextView.text = cast.name
    }
}